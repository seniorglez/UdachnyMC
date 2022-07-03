package com.seniorglez.domain;

import com.seniorglez.domain.model.User;

public interface TokenManager {

    public String getTokenFrom(User user);

    public boolean validate(String token);

}
