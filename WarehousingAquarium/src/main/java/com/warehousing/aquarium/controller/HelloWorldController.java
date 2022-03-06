package com.warehousing.aquarium.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
public class HelloWorldController {

    @RequestMapping({"/hello"})
    @GetMapping
    public String hello() {
        return "Hello World";
    }

}
