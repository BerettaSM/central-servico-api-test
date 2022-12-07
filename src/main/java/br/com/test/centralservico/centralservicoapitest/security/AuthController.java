package br.com.test.centralservico.centralservicoapitest.security;

import br.com.test.centralservico.centralservicoapitest.domain.dto.AuthCredentialsRequestDto;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import br.com.test.centralservico.centralservicoapitest.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody AuthCredentialsRequestDto request) {

        try {

            Authentication auth = authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(

                            request.getUsername(), request.getPassword()

                    )

            );

            User user = (User) auth.getPrincipal();

            user.setPassword(null);

            return ResponseEntity.status(HttpStatus.OK).header(

                    HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user)

            ).body(user);

        } catch(BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token, @AuthenticationPrincipal User user) {

        Boolean isTokenValid;

        try {

            isTokenValid = jwtUtil.validateToken(token, user);

        } catch(ExpiredJwtException e) {

            isTokenValid = false;

        }

        return ResponseEntity.status(HttpStatus.OK).body(isTokenValid);

    }

}
