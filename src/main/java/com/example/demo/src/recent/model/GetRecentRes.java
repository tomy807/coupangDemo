package com.example.demo.src.recent.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRecentRes {
    private String itemName;
    private int price;
    private String deliveryType;
}
