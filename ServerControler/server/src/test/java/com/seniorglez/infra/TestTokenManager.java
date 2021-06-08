package com.seniorglez.infra;

import com.seniorglez.aplication.login.QueryUser;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestTokenManager {

    private static final TokenManager tokenManager = new TokenManager();

    @Test
    public void testSmokeCreateToken() {
        tokenManager.getTokenFrom(new QueryUser("UwU", "OwO"));
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

}
