package com.seniorglez.infra;

import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import com.seniorglez.infra.repositories.UserRepositoryJDBC;
import com.seniorglez.domain.model.User;

import org.junit.Ignore;
import org.junit.Test;


import static org.junit.Assert.assertTrue;

/**
 *
 * Execute before docker-compose up -d db to start the database
 *
 */
public class TestRealUserRepositoryJDBC {

    @Ignore
    @Test
    public void testGetDefaultUser() {
        String username = "guest";
        Result res = new UserRepositoryJDBC().getUserByUsername("guest");
        assertTrue(res instanceof Result.Success);
        assertTrue(((Result.Success<User, UserErrors>)res).getValue().getUsername().equals(username));
    }

}
