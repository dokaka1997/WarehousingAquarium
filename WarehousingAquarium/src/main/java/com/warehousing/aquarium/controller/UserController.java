package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.model.request.CreateUserRequest;
import com.warehousing.aquarium.model.response.UserResponse;
import com.warehousing.aquarium.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<Boolean> forgotPassword(@RequestParam String email) {
        String token = RandomString.make(30);
        return ResponseEntity.ok(userService.forgotPassword(email, token));
    }

    @PostMapping("/change_password")
    public ResponseEntity<Boolean> forgotPassword(@RequestParam String email, @RequestParam String password, @RequestParam String token) {
        return ResponseEntity.ok(userService.changePassword(email, password, token));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @GetMapping("/token")
    public ResponseEntity<Boolean> checkToken(@RequestParam String token, @RequestParam Long userId) {
        return ResponseEntity.ok(userService.checkToken(token, userId));
    }


}
