package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private int productIdx;
    private int deliveryIdx;
    private int sellerIdx;
    private int itemIdx;
    private String productImgUrl;
    private int price;
    private int discount;
    private int membershipDiscount;
    private int stock;
    private String productStatus;
    private int totalRateSum;
    private int totalReviewCount;

}
