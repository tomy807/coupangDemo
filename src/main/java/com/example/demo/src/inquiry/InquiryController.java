package com.example.demo.src.inquiry;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.inquiry.model.GetInquiryRes;
import com.example.demo.src.inquiry.model.PatchInquiryReq;
import com.example.demo.src.inquiry.model.PostInquiryReq;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;
    private final InquiryProvider inquiryProvider;
    private final JwtService jwtService;

    @GetMapping("/{productIdx}")
    public BaseResponse<List<GetInquiryRes>> getInquiryByProduct(@PathVariable int productIdx) {
        try {
            List<GetInquiryRes> inquiries = inquiryProvider.getInquiryByProduct(productIdx);
            return new BaseResponse<>(inquiries);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("/{productIdx}")
    public BaseResponse<String> createInquiry(@PathVariable int productIdx, @RequestBody PostInquiryReq postInquiryReq) {

        if (postInquiryReq.getQuestion() == null) {
            return new BaseResponse<>(POST_INQUIRY_EMPTY_QUESTION);
        }

        try {

//            int userIdxByJwt = jwtService.getUserIdx();
//
//            if (postInquiryReq.getUserIdx() != userIdxByJwt) {
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            String result = "문의가 등록되었습니다.";

            inquiryService.createInquiry(postInquiryReq.getUserIdx(), productIdx, postInquiryReq);

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("/{inquiryIdx}")
    public BaseResponse<String> createAnswer(@PathVariable int inquiryIdx, @RequestBody PatchInquiryReq patchInquiryReq) {
        if (patchInquiryReq.getAnswer() == null) {
            return new BaseResponse<>(POST_INQUIRY_EMPTY_ANSWER);
        }
        try {
            String result = "답변이 등록되었습니다.";

            inquiryService.createAnswer(inquiryIdx,patchInquiryReq);

            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
