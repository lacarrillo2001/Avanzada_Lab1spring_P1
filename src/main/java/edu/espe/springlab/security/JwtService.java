package edu.espe.springlab.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final Algorithm algorithm;
    private final String issuer;
    private final long expirationMinutes;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.expirationMinutes = expirationMinutes;
    }

    public String generate(String subject, List<String> roles, String scope) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(subject)                 // sub
                .withClaim("roles", roles)            // roles
                .withClaim("scope", scope)            // scopes: "students:read students:write"
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(expirationMinutes * 60)))
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) {
        return JWT.require(algorithm).withIssuer(issuer).build().verify(token);
    }
}
