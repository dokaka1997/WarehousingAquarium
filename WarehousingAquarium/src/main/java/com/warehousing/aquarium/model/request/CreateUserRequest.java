package com.warehousing.aquarium.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String name;
    private Date dob;
    private String email;
    private String phone;
    private String address;
    private boolean isActive;
    private String avatar;
    private int roleId;
}
