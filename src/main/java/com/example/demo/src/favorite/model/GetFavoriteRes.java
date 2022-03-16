package com.example.demo.src.favorite.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetFavoriteRes {

    private int productIdx;
    private String itemName;
    private String deliveryType;
    private int price;

}
