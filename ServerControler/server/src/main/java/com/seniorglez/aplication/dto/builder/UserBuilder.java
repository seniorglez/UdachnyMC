package com.seniorglez.aplication.dto.builder;

import com.seniorglez.aplication.dto.UserDTO;
import com.seniorglez.domain.model.User;

public interface UserBuilder {

    UserDTO getUserDTOFromUser(User user);
}
