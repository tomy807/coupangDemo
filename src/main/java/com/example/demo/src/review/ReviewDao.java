package com.example.demo.src.review;


import com.example.demo.src.review.model.GetProductReviewsRes;
import com.example.demo.src.review.model.GetUserReviewsRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserReviewsRes> getUserReviews(int userIdx) {
        String getUserReviewQuery = "select item_name,rate,DATE_FORMAT(Review.createdAt, '%Y.%c.%e') as createdAt\n" +
                "from Review\n" +
                "         inner join OrderItem OI on Review.orderitem_id = OI.orderitem_id\n" +
                "         inner join Orders O on OI.order_id = O.order_id\n" +
                "         inner join Product P on OI.product_id = P.product_id\n" +
                "         inner join Item I on P.item_id = I.item_id\n" +
                "where user_id =?";

        List<GetUserReviewsRes> getUserReviewsRes = jdbcTemplate.query(getUserReviewQuery,
                (rs, rowNum) -> new GetUserReviewsRes(
                        rs.getString("item_name"),
                        rs.getInt("rate"),
                        rs.getString("createdAt")), userIdx);

        return getUserReviewsRes;
    }

    public List<GetProductReviewsRes> getProductReviews(int productIdx) {
        String getProductReviewQuery = "select text,Review.createdAt,email\n" +
                "from Review\n" +
                "         inner join OrderItem OI on Review.orderitem_id = OI.orderitem_id\n" +
                "         inner join Product P on OI.product_id = P.product_id\n" +
                "         inner join Orders O on OI.order_id = O.order_id\n" +
                "         inner join User U on O.user_id = U.user_id\n" +
                "where P.product_id=?";
        List<GetProductReviewsRes> getProductReviewsRes = jdbcTemplate.query(getProductReviewQuery,
                (rs, rowNum) -> new GetProductReviewsRes(
                        rs.getString("text"),
                        rs.getString("createdAt"),
                        rs.getString("email")
                ),
                productIdx);
        return getProductReviewsRes;
    }

    public int createReview(PostReviewReq postReviewReq) {
        String createReviewQuery = "insert into Review(orderitem_id, text, rate) VALUES (?,?,?)";
        Object[] createReviewParams = new Object[]{postReviewReq.getOrderItemIdx(), postReviewReq.getText(), postReviewReq.getRate()};
        return this.jdbcTemplate.update(createReviewQuery, createReviewParams);
    }

    public int modifyReview(PatchReviewReq patchReviewReq) {
        String createReviewQuery = "update Review set text=?,rate=? where orderitem_id=?";
        Object[] createReviewParams = new Object[]{patchReviewReq.getText(), patchReviewReq.getRate(), patchReviewReq.getOrderItemIdx()};
        return this.jdbcTemplate.update(createReviewQuery, createReviewParams);

    }
}
