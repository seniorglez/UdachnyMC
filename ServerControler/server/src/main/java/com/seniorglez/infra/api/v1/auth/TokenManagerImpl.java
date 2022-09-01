package com.seniorglez.infra.api.v1.auth;

import com.seniorglez.domain.TokenManager;
import com.seniorglez.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.crypto.SecretKey;
import java.util.Date;

import static java.util.Objects.isNull;

public class TokenManagerImpl implements TokenManager {

    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Logger logger = LoggerFactory.getLogger(TokenManagerImpl.class);

    @Override
    public String getTokenFrom(User user) {
        return Jwts.builder()
                .signWith( secretKey )
                .setSubject(user.getUsername())
                .setIssuedAt( new Date() )
                .setExpiration( new Date(System.currentTimeMillis() + 604800000 ))
                .compact();
    }

    @Override
    public boolean validate(String token) {
        if ( isNull(token) || token.isEmpty() ) {
            return false;
        }
        try {
            Jws<Claims> jwt = decodeJWT(token);
            Date expirationDate = jwt.getBody().getExpiration();
            return expirationDate.after(new Date());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    private Jws<Claims> decodeJWT(String token) {
        if (isNull(token) || token.isEmpty()) {
            return null;
        }
        Jws<Claims> jws;
        jws = Jwts.parserBuilder()
                .setSigningKey( secretKey )
                .build()
                .parseClaimsJws(token);
        return jws;
    }

}
