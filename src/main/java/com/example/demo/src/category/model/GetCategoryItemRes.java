package com.example.demo.src.category.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCategoryItemRes {

    private String itemName;
    private int price;
    private int discount;
    private String  deliveryType;

}
