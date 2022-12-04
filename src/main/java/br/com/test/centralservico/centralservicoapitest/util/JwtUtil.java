package br.com.test.centralservico.centralservicoapitest.util;

import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final long JWT_TOKEN_VALIDITY = 30 * 24 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);

    }

    public Date getIssuedAtDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getIssuedAt);

    }

    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);

    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);

    }

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                   .setSigningKey(secret)
                   .parseClaimsJws(token)
                   .getBody();

    }

    private Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);

        Date now = new Date();

        return expiration.before(now);

    }

    private Boolean ignoreTokenExpiration(String token) {

        return false;

    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("authorities", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        claims.put("fullName", extractFullNameFromUserDetails(userDetails));

        claims.put("userId", extractUserIdFromUserDetails(userDetails));

        return doGenerateToken(claims, userDetails.getUsername());

    }

    private String extractFullNameFromUserDetails(UserDetails userDetails) {

        try {

            return  ((User) userDetails).getFullName();

        } catch(ClassCastException e) {

            return null;

        }

    }

    private Long extractUserIdFromUserDetails(UserDetails userDetails) {

        try {

            return  ((User) userDetails).getId();

        } catch(ClassCastException e) {

            return -1L;

        }

    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public Boolean canTokenBeRefreshed(String token) {

        return (!isTokenExpired(token) || ignoreTokenExpiration(token));

    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        if (!StringUtils.hasText(token))
            return false;

        final String username = getUsernameFromToken(token);

        return (userDetails != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

}
