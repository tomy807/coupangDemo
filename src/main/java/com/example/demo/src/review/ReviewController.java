package com.example.demo.src.review;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.review.model.GetProductReviewsRes;
import com.example.demo.src.review.model.GetUserReviewsRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewProvider reviewProvider;
    private final ReviewService reviewService;
    private final JwtService jwtService;

    @GetMapping("")
    public BaseResponse<List<GetUserReviewsRes>> getUserReviews(@RequestParam int userIdx) {
        try {

            int userIdxByJwt = jwtService.getUserIdx();

            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetUserReviewsRes> productReviews = reviewProvider.getUserReviews(userIdx);
            return new BaseResponse<>(productReviews);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/{productIdx}")
    public BaseResponse<List<GetProductReviewsRes>> getProductReviews(@PathVariable int productIdx) {
        try {
            List<GetProductReviewsRes> productReviews = reviewProvider.getProductReviews(productIdx);
            return new BaseResponse<>(productReviews);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("")
    public BaseResponse<String> createReview(@RequestParam int userIdx, @RequestBody PostReviewReq postReviewReq) {
        if (postReviewReq.getOrderItemIdx() == null) {
            return new BaseResponse<>(POST_REVIEW_EMPTY_ORDERITEM);
        }
        if (postReviewReq.getText() == null) {
            return new BaseResponse<>(POST_REVIEW_EMPTY_TEXT);
        }
        if (postReviewReq.getRate() == null) {
            return new BaseResponse<>(POST_REVIEW_EMPTY_RATE);
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            String result = "리뷰가 생성됐습니다.";
            reviewService.createReview(postReviewReq);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @PatchMapping("")
    public BaseResponse<String> modifyReview(@RequestParam int userIdx, @RequestBody PatchReviewReq patchReviewReq) {

        if (patchReviewReq.getOrderItemIdx() == null) {
            return new BaseResponse<>(PATCH_REVIEW_EMPTY_ORDERITEM);
        }
        if (patchReviewReq.getText() == null) {
            return new BaseResponse<>(PATCH_REVIEW_EMPTY_TEXT);
        }
        if (patchReviewReq.getRate() == null) {
            return new BaseResponse<>(PATCH_REVIEW_EMPTY_RATE);
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            String result = "리뷰가 수정되었습니다.";
            reviewService.modifyReview(patchReviewReq);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
