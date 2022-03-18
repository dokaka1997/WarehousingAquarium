package com.warehousing.aquarium.model.response;

import com.warehousing.aquarium.entity.AccountEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwtToken;
    AccountEntity account;


    public JwtResponse(String jwtToken, AccountEntity account) {
        this.jwtToken = jwtToken;
        this.account = account;
    }



}