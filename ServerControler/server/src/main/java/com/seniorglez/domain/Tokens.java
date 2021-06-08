package com.seniorglez.domain;

import com.seniorglez.aplication.login.QueryUser;

public interface Tokens {

    public String getTokenFrom(QueryUser user);

    public boolean validate(String token);

}
