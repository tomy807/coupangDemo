package com.example.demo.src.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOtherProductRes {

    private String sellerName;
    private int price;
    private String deliveryType;
}
