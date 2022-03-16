package com.example.demo.src.category;

import com.example.demo.src.category.model.GetCategoryItemRes;
import com.example.demo.src.category.model.GetCategoryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetCategoryRes> getCategory() {
        String getItemsQuery = "select CL.categorylarge_id,CM.categorymiddle_id,CS.categorysmall_id,categorylarge_name,categorymiddle_name,categorysmall_name\n" +
                "from Category\n" +
                "         inner join CategorySmall CS on Category.categorysmall_id = CS.categorysmall_id\n" +
                "         inner join CategoryMiddle CM on CS.categorymiddle_id = CM.categorymiddle_id\n" +
                "         inner join CategoryLarge CL on CM.categorylarge_id = CL.categorylarge_id;";
        return jdbcTemplate.query(getItemsQuery,
                (rs, rowNum) -> new GetCategoryRes(
                        rs.getInt("categorylarge_id"),
                        rs.getInt("categorymiddle_id"),
                        rs.getInt("categorysmall_id"),
                        rs.getString("categorylarge_name"),
                        rs.getString("categorymiddle_name"),
                        rs.getString("categorysmall_name")
                ));
    }

    public List<GetCategoryItemRes> getItemsByLargeCategory(int categoryLargeIdx) {
        String getItemsQuery = "select item_name,min(price) as price,  discount, delivery_type\n" +
                "from Item\n" +
                "         inner join Category C on Item.category_id = C.category_id\n" +
                "         inner join Product P on Item.item_id = P.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "where categorylarge_id = ? \n" +
                "group by P.item_id;";
        return jdbcTemplate.query(getItemsQuery,
                (rs, rowNum) -> new GetCategoryItemRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getString("delivery_type")
                ), categoryLargeIdx);
    }

    public List<GetCategoryItemRes> getItemsByMiddleCategory(int categoryLargeIdx, int categoryMiddleIdx) {
        String getItemsQuery = "select item_name,min(price) as price,  discount, delivery_type\n" +
                "from Item\n" +
                "         inner join Category C on Item.category_id = C.category_id\n" +
                "         inner join Product P on Item.item_id = P.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "where categorylarge_id = ? and categorymiddle_id = ? \n" +
                "group by P.item_id;";
        return jdbcTemplate.query(getItemsQuery,
                (rs, rowNum) -> new GetCategoryItemRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getString("delivery_type")
                ), categoryLargeIdx, categoryMiddleIdx);
    }

    public List<GetCategoryItemRes> getItemsBySmallCategory(int categoryLargeIdx, int categoryMiddleIdx,int categorySmallIdx) {
        String getItemsQuery = "select item_name,min(price) as price,  discount, delivery_type\n" +
                "from Item\n" +
                "         inner join Category C on Item.category_id = C.category_id\n" +
                "         inner join Product P on Item.item_id = P.item_id\n" +
                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
                "where categorylarge_id = ? and categorymiddle_id = ? and categorysmall_id = ?\n" +
                "group by P.item_id;";
        return jdbcTemplate.query(getItemsQuery,
                (rs, rowNum) -> new GetCategoryItemRes(
                        rs.getString("item_name"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getString("delivery_type")
                ), categoryLargeIdx, categoryMiddleIdx, categorySmallIdx);
    }

//    public List<GetCategoryItemRes> getItemsByLargeCategory(int categoryLargeIdx) {
//        int getItemsByCategoryLargeIdxParams = categoryLargeIdx;
//        String getItemsQuery = "select item_name, min(price) as price, discount, delivery_type\n" +
//                "from Item\n" +
//                "         inner join Product P on Item.item_id = P.item_id\n" +
//                "         inner join Delivery D on P.delivery_id = D.delivery_id\n" +
//                "         inner join Category C on Item.category_id = C.category_id\n" +
//                "         inner join CategoryLarge CL on C.categorylarge_id = CL.categorylarge_id\n" +
//                "where CL.categorylarge_id = ? \n" +
//                "group by P.item_id;";
//        return jdbcTemplate.query(getItemsQuery,
//                (rs, rowNum) -> new GetCategoryItemRes(
//                        rs.getString("item_name"),
//                        rs.getInt("price"),
//                        rs.getInt("discount"),
//                        rs.getString("delivery_type")
//                ),getItemsByCategoryLargeIdxParams);
//    }


//    public List<GetCategoryItemRes> getItemsByCategory(int categoryIdx) {
//        int getItemsByCategoryIdxParams = categoryIdx;
//        String getItemsQuery = "select item_name, min(price) as price,discount, delivery_type \n" +
//                    "from Item \n" +
//                    "         inner join Product P on Item.item_id = P.item_id \n" +
//                    "         inner join Delivery D on P.delivery_id = D.delivery_id \n" +
//                    "         inner join Category C on Item.category_id = C.category_id \n" +
//                    "where C.category_id=? \n" +
//                    "group by P.item_id";
//        return jdbcTemplate.query(getItemsQuery,
//                (rs, rowNum) -> new GetCategoryItemRes(
//                        rs.getString("item_name"),
//                        rs.getInt("price"),
//                        rs.getInt("discount"),
//                        rs.getString("delivery_type")
//        ),getItemsByCategoryIdxParams);
//    }









}
