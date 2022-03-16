package com.example.demo.src.inquiry.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetInquiryRes {
    private String email;
    private String productName;
    private String question;
    private String answer;
    private String questionDay;
    private String answerDay;
}
