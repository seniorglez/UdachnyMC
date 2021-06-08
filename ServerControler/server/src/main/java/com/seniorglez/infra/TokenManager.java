package com.seniorglez.infra;

import com.seniorglez.aplication.login.QueryUser;
import com.seniorglez.domain.Tokens;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class TokenManager implements Tokens {

    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Override
    public String getTokenFrom(QueryUser user) {
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

    private Jws<Claims> decodeJWT(String token ) {
        if ( token == null || token.isEmpty() ) return null;
        Jws<Claims> jws;
        jws = Jwts.parserBuilder()
                .setSigningKey( secretKey )
                .build()
                .parseClaimsJws(token);
        return jws;
    }

}
