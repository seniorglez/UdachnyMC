package com.seniorglez.aplication.useCases.loginUser;

import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;

public class LoginUserOutput {

    private Result< String, UserErrors > result;

    public LoginUserOutput(Result< String, UserErrors > result) {
        this.result = result;
    }

    public Result< String, UserErrors > getResult() {
        return result;
    }
    
}
