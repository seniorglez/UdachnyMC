package com.seniorglez.aplication.login;

import com.seniorglez.domain.Users;
import com.seniorglez.domain.model.User;
import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.functionalJava.monads.Result;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;

public class TestLoginUser {

    @Mock
    Users users = Mockito.mock(Users.class);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConnection() {
        Mockito.when(users.getUserByUsername("pepe")).thenReturn(new Result.Success< User, UserErrors >(new User("pepe","pass")));
        Result res = new LoginUser(users).execute(new QueryUser("pepe","pass"));
        assertTrue(res instanceof Result.Success);
    }

}
