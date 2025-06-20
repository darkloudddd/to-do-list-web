package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "login";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        if (userService.authenticate(username, password)) {
            session.setAttribute("username", username); // Store user in session
            return "redirect:/tasks";
        } else {
            model.addAttribute("message", "Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerPage(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message);
        return "register";
    }

    @PostMapping("/auth/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            userService.register(username, password);
            model.addAttribute("message", "User registered! Please login.");
            return "login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", "Username already exists.");
            return "register";
        }
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}