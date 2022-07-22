package com.seniorglez.infra.api.v1.auth;
import com.seniorglez.domain.TokenManager;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.logging.Logger;

import static com.seniorglez.infra.api.v1.Global.LOGIN_ENDPOINT;
import static java.util.Objects.isNull;
import static spark.Spark.halt;

/**
 *
 * MIT License
 *
 * Copyright (c) 2018 rjozefowicz
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Checkout the original project this file was "forked" from:
 *
 * https://github.com/rjozefowicz/sparkjava-jwt/blob/master/LICENSE
 *
 * */
public class AuthFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(AuthFilter.class.getName());

    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HTTP_POST = "POST";

    private final String authEndpointPrefix;

    private TokenManager tokenManager;

    public AuthFilter(String authEndpointPrefix, TokenManager tokenManager) {
        this.authEndpointPrefix = authEndpointPrefix;
        this.tokenManager = tokenManager;
    }

    public AuthFilter(String authEndpointPrefix) {
        this(authEndpointPrefix, new TokenManagerImpl());
    }

    public void handle(Request request, Response response) {
        if (!isLoginRequest(request)) {
            String authorizationHeader = request.headers("Authorization");
            if (isNull(authorizationHeader)) {
                LOG.warning("Missing Authorization header");
                halt(401);
            } else if (!tokenManager.validate(authorizationHeader.replace(TOKEN_PREFIX, ""))) {
                LOG.warning("Expired token: " + authorizationHeader);
                halt(401);
            }
        }
    }

    private boolean isLoginRequest(Request request) {
        return request.uri().equals(authEndpointPrefix + LOGIN_ENDPOINT) && request.requestMethod().equals(HTTP_POST);
    }

}
