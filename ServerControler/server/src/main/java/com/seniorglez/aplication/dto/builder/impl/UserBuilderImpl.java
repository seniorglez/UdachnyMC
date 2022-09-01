package com.seniorglez.aplication.dto.builder.impl;

import com.seniorglez.aplication.dto.UserDTO;
import com.seniorglez.aplication.dto.builder.UserBuilder;
import com.seniorglez.domain.model.User;

import static java.util.Objects.isNull;

public class UserBuilderImpl implements UserBuilder {
    @Override
    public UserDTO getUserDTOFromUser(User user) {
        if(isNull(user)) {
            return null;
        }
        return new UserDTO(user.getUsername());
    }
}
