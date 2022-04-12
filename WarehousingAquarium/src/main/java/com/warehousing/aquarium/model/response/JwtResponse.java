package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    AccountDTO account;

    public JwtResponse(String jwtToken, AccountDTO account) {
        this.jwtToken = jwtToken;
        this.account = account;
    }
}