package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchProductReq {
    private int productIdx;
    private String productImgUrl;
    private int price;
    private int discount;
    private int membershipDiscount;
    private int stock;
    private String productStatus;


}
