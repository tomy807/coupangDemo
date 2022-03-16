package com.example.demo.src.product;


import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.GetProductRes;
import com.example.demo.src.review.model.GetProductReviewsRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ProductProvider {

    private final ProductDao productDao;

    public GetProductRes getProduct(int productIdx) throws BaseException {
        try {
            GetProductRes getProductRes = productDao.getProduct(productIdx);
            return getProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int getPrice(int productIdx) throws BaseException {
        try {
            return productDao.getPrice(productIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
