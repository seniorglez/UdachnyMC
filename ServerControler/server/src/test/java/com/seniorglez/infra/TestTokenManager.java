package com.seniorglez.infra;

import com.seniorglez.domain.model.User;
import com.seniorglez.infra.api.v1.auth.TokenManagerImpl;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestTokenManager {

    private static final TokenManagerImpl tokenManager = new TokenManagerImpl();

    @Test
    public void testSmokeCreateToken() {
        tokenManager.getTokenFrom(new User("foo","foo"));
    }

    @Test
    public void testValidateTokenShouldReturnFalse() {
        assertTrue(!tokenManager.validate("Vegetarianomon/Veganomon/Mineralomon"));
    }

    @Test
    public void testValidateNullTokenShouldReturnFalse() {
        assertTrue(!tokenManager.validate(null));
    }

    @Test
    public void testValidateEmptyTokenShouldReturnFalse() {
        assertTrue(!tokenManager.validate(""));
    }

    @Test
    public void testShouldReturnTrue() {
        String token = tokenManager.getTokenFrom(new User("Macaulay_Culkin", "HomeAlone1980"));
        assertTrue(tokenManager.validate(token));
    }

}
