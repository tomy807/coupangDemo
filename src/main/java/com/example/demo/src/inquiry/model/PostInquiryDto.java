package com.example.demo.src.inquiry.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostInquiryDto {

    private int productIdx;
    private int userIdx;
    private String question;


}
