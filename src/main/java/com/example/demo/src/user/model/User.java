package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private int point;
    private String provider;

}
