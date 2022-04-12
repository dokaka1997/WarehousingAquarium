package com.warehousing.aquarium.service;

import com.warehousing.aquarium.model.request.JwtRequest;
import com.warehousing.aquarium.model.response.JwtResponse;

public interface TokenService {
    JwtResponse generateToken(JwtRequest authenticationRequest) throws Exception;
}
