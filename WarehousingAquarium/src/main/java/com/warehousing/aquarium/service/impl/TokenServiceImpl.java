package com.warehousing.aquarium.service.impl;


import com.warehousing.aquarium.config.JwtTokenUtil;
import com.warehousing.aquarium.model.request.JwtRequest;
import com.warehousing.aquarium.model.request.LoginUserRequest;
import com.warehousing.aquarium.model.response.JwtResponse;
import com.warehousing.aquarium.service.TokenService;
import com.warehousing.aquarium.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenServiceImpl implements TokenService {


    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService jwtInMemoryUserDetailsService;

    private UserService userService;

    private AuthenticationManager authenticationManager;


    @Autowired
    public TokenServiceImpl(JwtTokenUtil jwtTokenUtil, UserDetailsService jwtInMemoryUserDetailsService,
                            UserService userService, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtInMemoryUserDetailsService = jwtInMemoryUserDetailsService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String getRefreshTokenById(Long id) {
        return null;
    }

    @Override
    public JwtResponse generateToken(JwtRequest authenticationRequest) throws Exception {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        LoginUserRequest userRequest = new LoginUserRequest();
        userRequest.setUsername(authenticationRequest.getUsername());
        userRequest.setPassword(authenticationRequest.getPassword());
        if (userService.login(userRequest)) {
            final UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            String refreshToken = "";
            //todo : get refreshToken
            return new JwtResponse(token, refreshToken, userDetails.getUsername());
        } else {
            throw new UsernameNotFoundException(userRequest.getUsername());
        }
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
