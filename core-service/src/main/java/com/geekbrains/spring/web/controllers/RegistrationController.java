package com.geekbrains.spring.web.controllers;

//import com.geekbrains.spring.web.auth.dto.UserRegistrationDto;
//import com.geekbrains.spring.web.entities.Role;
//import com.geekbrains.spring.web.services.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/api/v1/registration")
//@RequiredArgsConstructor
//public class RegistrationController {
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    @PostMapping
//    public void registration(@RequestBody UserRegistrationDto userRegistrationDto) {
//        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
//        userService.createNewUser(userRegistrationDto);
//    }
//}
