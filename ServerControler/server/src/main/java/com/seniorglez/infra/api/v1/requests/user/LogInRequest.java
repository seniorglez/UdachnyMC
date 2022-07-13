package com.seniorglez.infra.api.v1.requests.user;

import com.seniorglez.aplication.useCases.loginUser.LoginUserInput;

public class LogInRequest {

    private String user;

    private String password;

    public LogInRequest(){}

    public LogInRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }


    public LoginUserInput toLoginUserInput() {
        return new LoginUserInput(user,password);
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
