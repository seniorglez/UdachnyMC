package com.seniorglez.aplication.services.impl;

import com.seniorglez.aplication.services.UserService;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;

import com.seniorglez.domain.UserRepository;
import com.seniorglez.domain.model.User;
import com.seniorglez.infra.repositories.UserRepositoryJDBC;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceImpl() {
        this.userRepository = new UserRepositoryJDBC();
    }

    @Override
    public User getUserFromCredentials(String username, String password) {
        Result<User, UserErrors> result = this.userRepository.getUserByUsername(username);
        if(result instanceof Result.Failure) {
            return null;
        }
        User user = ((Result.Success<User, UserErrors>)result).getValue();
        if(!password.equals(user.getPassword())) {
            return  null;
        }
        return user;
    }
    @Override
    public User getUserByUsername(String username) {
        Result<User, UserErrors> result = this.userRepository.getUserByUsername(username);
        if(result instanceof Result.Failure) {
            return null;
        }
        return ((Result.Success<User, UserErrors>)result).getValue();
    }
}
