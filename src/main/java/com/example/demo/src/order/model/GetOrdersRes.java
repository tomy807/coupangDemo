package com.example.demo.src.order.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrdersRes {
    private int orderIdx;
    private String orderDay;
    private List<GetOrderItemRes> getOrderItemRes;
}
