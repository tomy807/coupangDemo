package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProductReq {
    private Integer deliveryIdx;
    private Integer sellerIdx;
    private Integer itemIdx;
    private String productImgUrl;
    private Integer price;
    private Integer discount;
    private Integer membershipDiscount;
    private Integer stock;
}
