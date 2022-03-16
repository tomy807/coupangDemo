package com.example.demo.src.category;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.category.model.GetCategoryItemRes;
import com.example.demo.src.category.model.GetCategoryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryProvider categoryProvider;

    private final CategoryService categoryService;

    //카테고리별 인덱스 확인
    @GetMapping("")
    public BaseResponse<List<GetCategoryRes>> getCategory() {
        try {
            List<GetCategoryRes> itemsByLargeCategory = categoryProvider.getCategory();
            return new BaseResponse<>(itemsByLargeCategory);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/{categoryIdx}")
    public BaseResponse<List<GetCategoryItemRes>> getItemByCategory(@PathVariable String categoryIdx) {
        List<GetCategoryItemRes> itemsByLargeCategory;
        String[] categorySplit = categoryIdx.split("_");
        try {
            if (categorySplit.length == 1) {
                itemsByLargeCategory = categoryProvider.getItemsByLargeCategory(Integer.parseInt(categorySplit[0]));
            } else if (categorySplit.length == 2) {
                itemsByLargeCategory = categoryProvider.getItemsByMiddleCategory(Integer.parseInt(categorySplit[0]), Integer.parseInt(categorySplit[1]));
            } else {
                itemsByLargeCategory = categoryProvider.getItemsBySmallCategory(Integer.parseInt(categorySplit[0]), Integer.parseInt(categorySplit[1]), Integer.parseInt(categorySplit[2]));
            }
            return new BaseResponse<>(itemsByLargeCategory);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //대분류
//    @GetMapping("/{categoryLargeIdx}")
//    public BaseResponse<List<GetCategoryItemRes>> getItemsByLargeCategory(@PathVariable int categoryLargeIdx) {
//        try {
//            List<GetCategoryItemRes> itemsByLargeCategory = categoryProvider.getItemsByLargeCategory(categoryLargeIdx);
//            return new BaseResponse<>(itemsByLargeCategory);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//    //중분류
//    @GetMapping("/{categoryLargeIdx}/{categoryMiddleIdx}")
//    public BaseResponse<List<GetCategoryItemRes>> getItemsByMiddleCategory(@PathVariable int categoryLargeIdx,@PathVariable int categoryMiddleIdx) {
//        try {
//            List<GetCategoryItemRes> itemsByLargeCategory = categoryProvider.getItemsByMiddleCategory(categoryLargeIdx,categoryMiddleIdx);
//            return new BaseResponse<>(itemsByLargeCategory);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//    //소분류
//    @GetMapping("/{categoryLargeIdx}/{categoryMiddleIdx}/{categorySmallIdx}")
//    public BaseResponse<List<GetCategoryItemRes>> getItemsBySmallCategory(@PathVariable int categoryLargeIdx,@PathVariable int categoryMiddleIdx,@PathVariable int categorySmallIdx) {
//        try {
//            List<GetCategoryItemRes> itemsByLargeCategory = categoryProvider.getItemsBySmallCategory(categoryLargeIdx,categoryMiddleIdx,categorySmallIdx);
//            return new BaseResponse<>(itemsByLargeCategory);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }



//    @GetMapping("")
//    public BaseResponse<List<GetCategoryItemRes>> getItemsByCategories(@RequestParam int large, @RequestParam int middle, @RequestParam int small) {
//
//        try {
//            List<GetCategoryItemRes> itemsByLargeCategory = categoryProvider.getItemsBySmallCategories(large,middle,small);
//            return new BaseResponse<>(itemsByLargeCategory);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
}
