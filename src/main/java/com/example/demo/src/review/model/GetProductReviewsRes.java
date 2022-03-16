package com.example.demo.src.review.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductReviewsRes {

    private String text;
    private String createdAt;
    private String email;
}
