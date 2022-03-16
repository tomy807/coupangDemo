package com.example.demo.src.recent;

import com.example.demo.src.recent.model.GetRecentRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RecentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetRecentRes> getRecent(int userIdx) {
        return jdbcTemplate.query("select item_name, price, delivery_type\n" +
                        "from RecentProduct\n" +
                        "         inner join Product P on RecentProduct.product_id = P.product_id\n" +
                        "         inner join Item I on P.item_id = I.item_id\n" +
                        "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                        "where user_id = ?",
                (rs, rowNum) -> new GetRecentRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getString("delivery_type")), userIdx);
    }

}
