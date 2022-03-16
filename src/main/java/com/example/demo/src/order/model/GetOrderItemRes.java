package com.example.demo.src.order.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderItemRes {

    private int orderItemIdx;
    private String deliveryStatus;
    private String deliveryAt;
    private String itemName;
    private int quantity;
    private int price;

}
