package com.example.demo.src.oauth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private String email;
    private String name;
    private String imageUrl;
    private String password;
}
