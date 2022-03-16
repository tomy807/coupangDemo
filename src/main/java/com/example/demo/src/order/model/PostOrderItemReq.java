package com.example.demo.src.order.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderItemReq {
    private Integer productIdx;
    private Integer quantity;
}
