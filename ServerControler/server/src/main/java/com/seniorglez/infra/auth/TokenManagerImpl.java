package com.seniorglez.infra.auth;

import com.seniorglez.domain.TokenManager;
import com.seniorglez.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class TokenManagerImpl implements TokenManager {

    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

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
        if ( token == null || token.isEmpty()) return false;
        try {
            Jws<Claims> jwt = decodeJWT(token);
            Date expirationDate = jwt.getBody().getExpiration();
            return expirationDate.after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Jws<Claims> decodeJWT(String token) {
        if ( token == null || token.isEmpty() ) return null;
        Jws<Claims> jws;
        jws = Jwts.parserBuilder()
                .setSigningKey( secretKey )
                .build()
                .parseClaimsJws(token);
        return jws;
    }

}
