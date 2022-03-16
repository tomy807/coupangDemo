package com.example.demo.src.inquiry;

import com.example.demo.config.BaseException;
import com.example.demo.src.inquiry.model.PatchInquiryReq;
import com.example.demo.src.inquiry.model.PostInquiryDto;
import com.example.demo.src.inquiry.model.PostInquiryReq;
import com.example.demo.src.product.ProductProvider;
import com.example.demo.src.product.model.GetProductRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryDao inquiryDao;


    public void createInquiry(int userIdx, int productIdx, PostInquiryReq postInquiryReq) throws BaseException {
        try {

            int result = inquiryDao.createInquiry(new PostInquiryDto(productIdx, userIdx, postInquiryReq.getQuestion()));
            if (result == 0) {
                throw new BaseException(CREATE_FAIL_INQUIRY);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public void createAnswer(int inquiryIdx, PatchInquiryReq patchInquiryReq) throws BaseException {
        try {

            int result = inquiryDao.createAnswer(inquiryIdx,patchInquiryReq);
            if (result == 0) {
                throw new BaseException(CREATE_FAIL_ANSWER);
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
