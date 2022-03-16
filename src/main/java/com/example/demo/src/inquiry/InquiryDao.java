package com.example.demo.src.inquiry;

import com.example.demo.src.inquiry.model.GetInquiryRes;
import com.example.demo.src.inquiry.model.PatchInquiryReq;
import com.example.demo.src.inquiry.model.PostInquiryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class InquiryDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetInquiryRes> getInquiryByProduct(int productIdx) {

        return jdbcTemplate.query("select email, item_name, user_inquiry, seller_answer, Inquiry.createdAt as questionDay, Inquiry.updatedAt as answerDay\n" +
                        "from Inquiry\n" +
                        "         inner join User U on Inquiry.user_id = U.user_id\n" +
                        "         inner join Product P on Inquiry.product_id = P.product_id\n" +
                        "         inner join Item I on P.item_id = I.item_id\n" +
                        "         inner join Seller S on P.seller_id = S.seller_id\n" +
                        "where P.product_id =?",
                (rs, rowNum) -> new GetInquiryRes(
                        rs.getString("email"),
                        rs.getString("item_name"),
                        rs.getString("user_inquiry"),
                        rs.getString("seller_answer"),
                        rs.getString("questionDay"),
                        rs.getString("answerDay")), productIdx);
    }

    public int createInquiry(PostInquiryDto postInquiryDto) {
        return jdbcTemplate.update("insert into Inquiry(product_id, user_id, user_inquiry) VALUES (?,?,?)",
                postInquiryDto.getProductIdx(), postInquiryDto.getUserIdx(), postInquiryDto.getQuestion());
    }

    public int createAnswer(int inquiryIdx, PatchInquiryReq patchInquiryReq) {
        return jdbcTemplate.update("update Inquiry set seller_answer=? where inquiry_id=?", patchInquiryReq.getAnswer(), inquiryIdx);
    }
}
