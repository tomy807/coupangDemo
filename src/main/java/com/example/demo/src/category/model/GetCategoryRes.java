package com.example.demo.src.category.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCategoryRes {
    private int categoryLargeIdx;
    private int categoryMiddleIdx;
    private int categorySmallIdx;
    private String categoryLargeName;
    private String categoryMiddleName;
    private String categorySmallName;
}
