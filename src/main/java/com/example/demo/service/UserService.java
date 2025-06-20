package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public UserService() {
        // Add a default user for testing
        users.put("admin", new User("admin", "password"));
    }

    public boolean authenticate(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public void register(String username, String password) {
        users.put(username, new User(username, password));
    }

    public java.util.Collection<User> getAllUsers() {
        return users.values();
    }
}