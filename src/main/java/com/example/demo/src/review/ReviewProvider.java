package com.example.demo.src.review;


import com.example.demo.config.BaseException;
import com.example.demo.src.review.model.GetProductReviewsRes;
import com.example.demo.src.review.model.GetUserReviewsRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ReviewProvider {

    private final ReviewDao reviewDao;

    public List<GetUserReviewsRes> getUserReviews(int userIdx) throws BaseException {
        try {
            List<GetUserReviewsRes> userReviews = reviewDao.getUserReviews(userIdx);
            return userReviews;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetProductReviewsRes> getProductReviews(int productIdx) throws BaseException {
        try {
            List<GetProductReviewsRes> productReviews = reviewDao.getProductReviews(productIdx);
            return productReviews;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
