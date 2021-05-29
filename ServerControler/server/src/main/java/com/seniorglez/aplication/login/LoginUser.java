package com.seniorglez.aplication.login;

import com.seniorglez.domain.Users;
import com.seniorglez.domain.model.User;
import com.seniorglez.functionalJava.monads.Option;

public class LoginUser {

    private final Users users;

    public LoginUser (Users users) {
       this.users = users;
    }

    public Option<User> execute(QueryUser queryUser) {
        String username = queryUser.getUsername();
        String password = queryUser.getPassword();
        if (username == null || password == null) return new Option<User>();
        if (username.isEmpty() || password.isEmpty()) {
            return new Option<User>();
        }
        User user = this.users.getUserByUsername(username);
        if (user == null) {
            return new Option<User>();
        }
        if(user.getPassword().equals(password)){new Option<User>(user);};
        return new Option<User>();
    }
}
