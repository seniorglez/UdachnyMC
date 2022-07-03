package com.seniorglez.aplication.services;

import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;

import com.seniorglez.domain.UserRepository;
import com.seniorglez.domain.model.User;
import com.seniorglez.infra.repositories.UserRepositoryJDBC;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {
        this.userRepository = new UserRepositoryJDBC();
    }

    public User getUser(String username, String password) {
        Result<User, UserErrors> result = this.userRepository.getUser(username,password);
        if(result instanceof Result.Failure) {
            return null;
        }
        return ((Result.Success<User, UserErrors>)result).getValue();
    }

    public User getUserByUsername(String username) {
        Result<User, UserErrors> result = this.userRepository.getUserByUsername(username);
        if(result instanceof Result.Failure) {
            return null;
        }
        return ((Result.Success<User, UserErrors>)result).getValue();
    }
}
