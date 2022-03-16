package com.example.demo.src.delivery.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDeliveryItemRes {

    private String itemName;
    private int price;
    private int discount;
    private String deliveryType;
    private Float rate;
    private int totalReviewCount;
}
