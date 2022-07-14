package com.seniorglez.aplication.services;

import com.seniorglez.domain.model.User;

public interface UserService {
    User getUserFromCredentials(String username, String password);

    User getUserByUsername(String username);
}
