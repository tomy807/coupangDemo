package com.example.demo.src.inquiry.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchInquiryReq {

    private int inquiryIdx;
    private String answer;
}
