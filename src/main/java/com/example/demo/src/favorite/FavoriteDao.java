package com.example.demo.src.favorite;


import com.example.demo.src.favorite.model.GetFavoriteRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FavoriteDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetFavoriteRes> getFavorite(int userIdx) {
        String getUserQuery = "select Favorite.product_id,item_name, delivery_type, price\n" +
                "from Favorite\n" +
                "         inner join Product P on Favorite.product_id = P.product_id\n" +
                "         inner join Item I on P.item_id = I.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "where user_id = ? and Favorite.status='SAVED'";
        int getUserParams = userIdx;
        return this.jdbcTemplate.query(getUserQuery,
                (rs, rowNum) -> new GetFavoriteRes(
                        rs.getInt("product_id"),
                        rs.getString("item_name"),
                        rs.getString("delivery_type"),
                        rs.getInt("price")),
                getUserParams);
    }

    public void createFavorite(int userIdx, int productIdx) {
        String createFavoriteQuery = "insert into Favorite(user_id, product_id) VALUES (?,?)";
        Object[] createFavoriteParams = new Object[]{userIdx, productIdx};
        this.jdbcTemplate.update(createFavoriteQuery, createFavoriteParams);

    }

    public void deleteFavorite(int userIdx, int productIdx) {
        String deleteFavoriteQuery = " update Favorite set status='DELETED' where user_id=? and product_id=?";
        Object[] deleteFavoriteParams = new Object[]{userIdx, productIdx};
        this.jdbcTemplate.update(deleteFavoriteQuery, deleteFavoriteParams);
    }
}
