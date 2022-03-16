package com.example.demo.src.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetProductRes {
    private String itemName;
    private String productImgUrl;
    private String brand;
    private String sellerName;
    private int price;
    private int discount;
    private float rate;
    private int totalReviewCount;
    private List<GetOtherProductRes> getOtherProductResList;
}
