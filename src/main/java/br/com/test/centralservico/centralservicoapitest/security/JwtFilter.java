package br.com.test.centralservico.centralservicoapitest.security;

import br.com.test.centralservico.centralservicoapitest.persistence.UserRepository;
import br.com.test.centralservico.centralservicoapitest.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!StringUtils.hasText(header) || (StringUtils.hasText(header) && !header.startsWith("Bearer "))) {

            filterChain.doFilter(request, response);

            return;

        }

        final String token = header.split(" ")[1].trim();

        UserDetails userDetails = userRepository.findByUsername(jwtUtil.getUsernameFromToken(token))
                                                .orElse(null);

        if(!jwtUtil.validateToken(token, userDetails)) {

            filterChain.doFilter(request, response);

            return;

        }

        Collection<? extends GrantedAuthority> userAuthorities = userDetails == null ?
                                                                 List.of() :
                                                                 userDetails.getAuthorities();

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                                                                                  null,
                                                                                           userAuthorities);

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);

    }

}
