package com.example.demo.src.product;

import com.example.demo.src.product.model.*;
import com.example.demo.src.review.model.GetProductReviewsRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetProductRes getProduct(int productIdx) {
        String getProductQuery1 = "select item_name,product_imgUrl,brand,seller_name,price,discount,(I.total_rate_sum/I.total_review_count) as rate,total_review_count\n" +
                "from Product P\n" +
                "        inner join Item I on P.item_id = I.item_id\n" +
                "        inner join Category C on I.category_id = C.category_id\n" +
                "        inner join Delivery D on P.delivery_id = D.delivery_id \n" +
                "        inner join Seller S on P.seller_id = S.seller_id\n" +
                "where P.product_id = ?";
        int getItemParams = productIdx;
        GetProductRes getProductRes = this.jdbcTemplate.queryForObject(getProductQuery1,
                (rs, rowNum) -> new GetProductRes(
                        rs.getString("item_name"),
                        rs.getString("product_imgUrl"),
                        rs.getString("brand"),
                        rs.getString("seller_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getFloat("rate"),
                        rs.getInt("total_review_count"),
                        null
                ),
                getItemParams);
        String getProductQuery2="select seller_name, price, delivery_type\n" +
                "from Product\n" +
                "         inner join (select I.item_id as i1\n" +
                "                     from Product\n" +
                "                              inner join Item I on Product.item_id = I.item_id\n" +
                "                     where product_id = ?) P1\n" +
                "                    on Product.item_id = P1.i1\n" +
                "         inner join Seller S on Product.seller_id = S.seller_id\n" +
                "         inner join Delivery D on Product.delivery_id = D.delivery_id;";
        List<GetOtherProductRes> getOtherProductRes = this.jdbcTemplate.query(getProductQuery2,
                (rs, rowNum) -> new GetOtherProductRes(
                        rs.getString("seller_name"),
                        rs.getInt("price"),
                        rs.getString("delivery_type")
                ),
                getItemParams);
        getProductRes.setGetOtherProductResList(getOtherProductRes);
        return getProductRes;
    }

    public int getPrice(int productIdx) {
        return jdbcTemplate.queryForObject("select price from Product where product_id=?", int.class, productIdx);
    }

    public void updateProductStock(int quantity, int productIdx) {
        jdbcTemplate.update("update Product set stock=stock-? where product_id=?", quantity, productIdx);
    }

    public int createProduct(PostProductReq postProductReq) {
        String createProductQuery = "insert into Product (delivery_id, seller_id, item_id, product_imgUrl, price, discount, membership_discount, stock)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createProductParams = new Object[]{postProductReq.getDeliveryIdx(), postProductReq.getSellerIdx(), postProductReq.getItemIdx(), postProductReq.getProductImgUrl(), postProductReq.getPrice(), postProductReq.getDiscount(), postProductReq.getMembershipDiscount(), postProductReq.getStock()};
        jdbcTemplate.update(createProductQuery, createProductParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public PatchProductRes modifyProduct(PatchProductReq patchProductReq) {
        int productIdx = patchProductReq.getProductIdx();

        String modifyProductQuery = "update Product\n" +
                "set product_imgUrl= ?,price = ?,discount = ?,membership_discount = ?,stock = ?,product_status=?\n" +
                "where product_id = ?";
        String modifiedProductQuery = "select * from Product where product_id=?;";

        Object[] modifyProductParams = new Object[]{
                patchProductReq.getProductImgUrl(),
                patchProductReq.getPrice(),
                patchProductReq.getDiscount(),
                patchProductReq.getMembershipDiscount(),
                patchProductReq.getPrice(),
                patchProductReq.getProductStatus(),
                productIdx};
        this.jdbcTemplate.update(modifyProductQuery, modifyProductParams);

        return this.jdbcTemplate.queryForObject(modifiedProductQuery,
                (rs, rowNum) -> new PatchProductRes(
                        rs.getString("product_imgUrl"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("membership_discount"),
                        rs.getInt("stock"),
                        rs.getString("product_status")),
                productIdx);
    }
}

