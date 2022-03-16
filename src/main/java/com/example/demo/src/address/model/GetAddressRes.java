package com.example.demo.src.address.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAddressRes {
    private String mainAddress;
    private String subAddress1;
    private String subAddress2;
}
