package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    private final String refreshToken;
    private final String username;

    public JwtResponse(String jwtToken, String refreshToken, String username) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }

}