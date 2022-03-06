package com.warehousing.aquarium.service;

import com.warehousing.aquarium.model.request.LoginUserRequest;
import com.warehousing.aquarium.model.request.CreateUserRequest;
import com.warehousing.aquarium.model.response.UserResponse;

public interface UserService {
    boolean createUser(CreateUserRequest userRequest);

    boolean login(LoginUserRequest loginUserRequest);
}
