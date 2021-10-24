package com.seniorglez.infra;

import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.domain.model.User;

import org.junit.Ignore;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

/**
 *
 * Execute before docker-compose up -d db to start the database
 *
 */
public class TestRealUserJDBC {

    @Ignore
    @Test
    public void testGetDefaultUser() {
        String username = "guest";
        Result res = new UserJDBC().getUserByUsername("guest");
        assertTrue(res instanceof Result.Success);
        assertTrue(((Result.Success<User, UserErrors>)res).getValue().getUsername().equals(username));
    }

}
