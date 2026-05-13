package com.example.eventmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.model.User;
import com.example.eventmanagement.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        if (service.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }
        return service.register(user);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        User u = service.login(username, password);

        if (u != null) {
            return u.getRole() != null ? u.getRole() : "USER"; // return role for frontend routing
        } else {
            throw new RuntimeException("Invalid Username or Password");
        }
    }
}