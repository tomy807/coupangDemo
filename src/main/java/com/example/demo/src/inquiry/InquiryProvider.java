package com.example.demo.src.inquiry;

import com.example.demo.config.BaseException;
import com.example.demo.src.inquiry.model.GetInquiryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class InquiryProvider {

    private final InquiryDao inquiryDao;

    public List<GetInquiryRes> getInquiryByProduct(int productIdx) throws BaseException {

        try {
            List<GetInquiryRes> inquiries = inquiryDao.getInquiryByProduct(productIdx);
            return inquiries;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
