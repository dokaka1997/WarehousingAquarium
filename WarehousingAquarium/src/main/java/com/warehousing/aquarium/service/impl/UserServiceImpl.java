package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.model.request.CreateUserRequest;
import com.warehousing.aquarium.model.request.LoginUserRequest;
import com.warehousing.aquarium.model.response.UserResponse;
import com.warehousing.aquarium.repository.UserRepository;
import com.warehousing.aquarium.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean createUser(CreateUserRequest userRequest) {
        ModelMapper mapper = new ModelMapper();
        AccountEntity userEntity = mapper.map(userRequest, AccountEntity.class);

        if (userRepository.getByUsername(userRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username existed");
        }
        try {
            userRepository.save(userEntity);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean login(LoginUserRequest loginUserRequest) {
        Optional<AccountEntity> userEntity = userRepository.getByUsernameAndPassword(loginUserRequest.getUsername(), loginUserRequest.getPassword());
        return userEntity.isPresent();
    }
}
