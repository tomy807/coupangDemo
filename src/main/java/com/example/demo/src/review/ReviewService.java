package com.example.demo.src.review;


import com.example.demo.config.BaseException;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewDao reviewDao;

    public void createReview(PostReviewReq postReviewReq) throws BaseException {
        try {
            int result = reviewDao.createReview(postReviewReq);
            if (result == 0) {
                throw new BaseException(CREATE_FAIL_REVIEW);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyReview(PatchReviewReq patchReviewReq) throws BaseException {
        try {
            int result = reviewDao.modifyReview(patchReviewReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_REVIEW);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
