package com.lingluoyu.controller;

import com.lingluoyu.mapper.Model.User;
import com.lingluoyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("get")
    public User getUser(@RequestParam Long id) {
        return userService.getUser(id);
    }

    @PostMapping("set")
    public int getUser(@RequestBody User user) {
        return userService.setUser(user);
    }
}
