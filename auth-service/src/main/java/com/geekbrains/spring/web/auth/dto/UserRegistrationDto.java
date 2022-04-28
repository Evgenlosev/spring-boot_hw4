package com.geekbrains.spring.web.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String password;


}
