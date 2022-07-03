package com.seniorglez.infra.api.v1.response.builder;

import com.seniorglez.domain.model.UserErrors;
import com.seniorglez.infra.api.v1.response.EndpointResponse;

public class UserErrorResponseBuilder {

    public static EndpointResponse getUserErrorEndpointResponse(UserErrors error) { //WIP
        return new EndpointResponse(400,"JSON","{ \"error\":\"Generic user error\"}");
    }
}
