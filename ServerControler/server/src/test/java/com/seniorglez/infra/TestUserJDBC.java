package com.seniorglez.infra;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.seniorglez.functionalJava.monads.Result;

public class TestUserJDBC {

    @Test
    public void realDBConnectionShouldBeSuccess()
    {
        assertTrue(new UserJDBC().getUserByUsername("guest") instanceof Result.Success);
    }
}