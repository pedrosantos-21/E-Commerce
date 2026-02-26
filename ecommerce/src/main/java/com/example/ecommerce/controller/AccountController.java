package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/accounts")

public class AccountController {

    @GetMapping("hello")
    public String helloWorld() {
        return "Hello World";
    }

}
