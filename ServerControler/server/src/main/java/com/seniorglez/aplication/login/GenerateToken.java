package com.seniorglez.aplication.login;

import com.seniorglez.domain.Tokens;

public class GenerateToken {

    private final Tokens tokens;

    public GenerateToken(Tokens tokens) {
        this.tokens = tokens;
    }

    public String execute(QueryUser user) {
        return tokens.getTokenFrom(user);
    }
}
