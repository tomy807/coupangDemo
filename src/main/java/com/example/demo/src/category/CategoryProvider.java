package com.example.demo.src.category;

import com.example.demo.config.BaseException;
import com.example.demo.src.category.model.GetCategoryItemRes;
import com.example.demo.src.category.model.GetCategoryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class CategoryProvider {

    private final CategoryDao categoryDao;


    public List<GetCategoryRes> getCategory() throws BaseException{
        try {
            List<GetCategoryRes> itemsByCategory = categoryDao.getCategory();
            return itemsByCategory;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<GetCategoryItemRes> getItemsByLargeCategory(int categoryLargeIdx) throws BaseException {
        try {
            List<GetCategoryItemRes> itemsByCategory = categoryDao.getItemsByLargeCategory(categoryLargeIdx);
            return itemsByCategory;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCategoryItemRes> getItemsByMiddleCategory(int categoryLargeIdx, int categoryMiddleIdx) throws BaseException {
        try {
            List<GetCategoryItemRes> itemsByLargeCategory = categoryDao.getItemsByMiddleCategory(categoryLargeIdx,categoryMiddleIdx);
            return itemsByLargeCategory;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetCategoryItemRes> getItemsBySmallCategory( int categoryLargeIdx,int categoryMiddleIdx,int categorySmallIdx) throws BaseException {

        try {
            List<GetCategoryItemRes> itemsByLargeCategory = categoryDao.getItemsBySmallCategory(categoryLargeIdx,categoryMiddleIdx,categorySmallIdx);
            return itemsByLargeCategory;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

//
//    public List<GetCategoryItemRes> getItemsByLargeCategories(int large) throws BaseException{
//        try {
//            List<GetCategoryItemRes> itemsByLargeCategory = categoryDao.getItemsByLargeCategories(large);
//            return itemsByLargeCategory;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }



//    public List<GetCategoryItemRes> getItemsByCategory(int categoryIdx) throws BaseException {
//        try {
//            List<GetCategoryItemRes> itemsByCategory = categoryDao.getItemsByCategory(categoryIdx);
//            return itemsByCategory;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//
//    }



}