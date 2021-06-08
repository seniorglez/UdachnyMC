package com.seniorglez.aplication.login;

import com.seniorglez.domain.Tokens;

public class ValidateToken {

    private final Tokens tokens;

    public  ValidateToken(Tokens tokens) {
        this.tokens = tokens;
    }

    public boolean execute(String token) {
        return tokens.validate(token);
    }
}
