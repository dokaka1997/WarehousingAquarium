package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.model.request.CreateUserRequest;
import com.warehousing.aquarium.model.request.LoginUserRequest;

public interface UserService {
    boolean createUser(CreateUserRequest userRequest);

    AccountEntity login(LoginUserRequest loginUserRequest);

    boolean forgotPassword(String email, String token);

    boolean changePassword(String email, String newPassword, String token);
}
