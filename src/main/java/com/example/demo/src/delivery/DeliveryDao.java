package com.example.demo.src.delivery;


import com.example.demo.src.delivery.model.GetDeliveryItemRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DeliveryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetDeliveryItemRes> getItemsByRocket() {
        String getItemsByRocketQuery = "select item_name, min(price) as price, discount, delivery_type, (total_rate_sum/total_review_count) as rate,total_review_count\n" +
                "from Item\n" +
                "         inner join Product P on Item.item_id = P.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "         inner join Category C on Item.category_id = C.category_id\n" +
                "         inner join CategoryLarge CL on C.categorylarge_id = CL.categorylarge_id\n" +
                "where D.delivery_id=1\n" +
                "group by P.item_id";
        return jdbcTemplate.query(getItemsByRocketQuery, (rs, rowNum) ->
                new GetDeliveryItemRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getString("delivery_type"),
                        rs.getFloat("rate"),
                        rs.getInt("total_review_count")
                ));
    }

    public List<GetDeliveryItemRes> getItemsByRocketByLargeCategory(int categoryLargeIdx) {
        String getItemsByRocketQuery = "select item_name, min(price) as price, discount, delivery_type, (total_rate_sum/total_review_count) as rate,total_review_count\n" +
                "from Item\n" +
                "         inner join Product P on Item.item_id = P.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "         inner join Category C on Item.category_id = C.category_id\n" +
                "         inner join CategoryLarge CL on C.categorylarge_id = CL.categorylarge_id\n" +
                "where D.delivery_id=1 and C.categorylarge_id=?\n" +
                "group by P.item_id";
        return jdbcTemplate.query(getItemsByRocketQuery, (rs, rowNum) ->
                new GetDeliveryItemRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getString("delivery_type"),
                        rs.getFloat("rate"),
                        rs.getInt("total_review_count")
                ), categoryLargeIdx);
    }

    public List<GetDeliveryItemRes> getItemsByRocketByMiddleCategory(int categoryLargeIdx,int categoryMiddleIdx) {
        String getItemsByRocketQuery = "select item_name, min(price) as price, discount, delivery_type, (total_rate_sum/total_review_count) as rate,total_review_count\n" +
                "from Item\n" +
                "         inner join Product P on Item.item_id = P.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "         inner join Category C on Item.category_id = C.category_id\n" +
                "         inner join CategoryLarge CL on C.categorylarge_id = CL.categorylarge_id\n" +
                "where D.delivery_id=1 and C.categorylarge_id=? and C.categoryMiddle_id=?\n" +
                "group by P.item_id";
        return jdbcTemplate.query(getItemsByRocketQuery, (rs, rowNum) ->
                new GetDeliveryItemRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getString("delivery_type"),
                        rs.getFloat("rate"),
                        rs.getInt("total_review_count")
                ), categoryLargeIdx, categoryMiddleIdx);
    }

    public List<GetDeliveryItemRes> getItemsByRocketBySmallCategory(int categoryLargeIdx,int categoryMiddleIdx,int categorySmallIdx) {
        String getItemsByRocketQuery = "select item_name, min(price) as price, discount, delivery_type, (total_rate_sum/total_review_count) as rate,total_review_count\n" +
                "from Item\n" +
                "         inner join Product P on Item.item_id = P.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "         inner join Category C on Item.category_id = C.category_id\n" +
                "         inner join CategoryLarge CL on C.categorylarge_id = CL.categorylarge_id\n" +
                "where D.delivery_id=1 and C.categorylarge_id=? and C.categoryMiddle_id=? and C.categorySmall_id=?\n" +
                "group by P.item_id";
        return jdbcTemplate.query(getItemsByRocketQuery, (rs, rowNum) ->
                new GetDeliveryItemRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getString("delivery_type"),
                        rs.getFloat("rate"),
                        rs.getInt("total_review_count")
                ), categoryLargeIdx, categoryMiddleIdx, categorySmallIdx);
    }
    public List<GetDeliveryItemRes> getItemsByFresh() {
        String getItemsByRocketQuery = "select item_name, min(price) as price, discount, delivery_type, (total_rate_sum/total_review_count) as rate,total_review_count\n" +
                "from Item\n" +
                "         inner join Product P on Item.item_id = P.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "         inner join Category C on Item.category_id = C.category_id\n" +
                "         inner join CategoryLarge CL on C.categorylarge_id = CL.categorylarge_id\n" +
                "where D.delivery_id=2\n" +
                "group by P.item_id";
        return jdbcTemplate.query(getItemsByRocketQuery, (rs, rowNum) ->
                new GetDeliveryItemRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getString("delivery_type"),
                        rs.getFloat("rate"),
                        rs.getInt("total_review_count")
                ));
    }

    public List<GetDeliveryItemRes> getItemsByCrossBorder() {
        String getItemsByRocketQuery = "select item_name, min(price) as price, discount, delivery_type, (total_rate_sum/total_review_count) as rate,total_review_count\n" +
                "from Item\n" +
                "         inner join Product P on Item.item_id = P.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "         inner join Category C on Item.category_id = C.category_id\n" +
                "         inner join CategoryLarge CL on C.categorylarge_id = CL.categorylarge_id\n" +
                "where D.delivery_id=3\n" +
                "group by P.item_id";
        return jdbcTemplate.query(getItemsByRocketQuery, (rs, rowNum) ->
                new GetDeliveryItemRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getString("delivery_type"),
                        rs.getFloat("rate"),
                        rs.getInt("total_review_count")
                ));
    }
}
