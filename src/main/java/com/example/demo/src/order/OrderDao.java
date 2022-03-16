package com.example.demo.src.order;


import com.example.demo.src.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetOrdersRes> getOrders(int userIdx) {

        String getOrderQuery = "select order_id,DATE_FORMAT(Orders.createdAt,'%Y.%c.%e 주문') as orderDay \n" +
                "from Orders\n" +
                "         inner join User U on Orders.user_id = U.user_id\n" +
                "where U.user_id = ? and Orders.status='SAVED' ";

        int getOrderParam = userIdx;

        List<GetOrdersRes> orderList = jdbcTemplate.query(getOrderQuery,
                (rs, rowNum) -> new GetOrdersRes(
                        rs.getInt("order_id"),
                        rs.getString("orderDay"),
                        null),
                getOrderParam);

        String getOrderItemQuery = "select orderitem_id,O.delivery_status,\n" +
                "       IF(delivery_status = 'COMPLETED', DATE_FORMAT(deliveredAt, '%c/%e(%a) 도착'), '배송중') as deliveredAt,\n" +
                "       item_name,quantity,price\n" +
                "from OrderItem\n" +
                "         inner join Orders O on OrderItem.order_id = O.order_id\n" +
                "         inner join Product P on OrderItem.product_id = P.product_id\n" +
                "         inner join Item I on P.item_id = I.item_id\n" +
                "where O.order_id=?";

        for (GetOrdersRes getOrdersRes : orderList) {
            List<GetOrderItemRes> orderItemList = jdbcTemplate.query(getOrderItemQuery,
                    (rs, rowNum) -> new GetOrderItemRes(
                            rs.getInt("orderitem_id"),
                            rs.getString("delivery_status"),
                            rs.getString("deliveredAt"),
                            rs.getString("item_name"),
                            rs.getInt("quantity"),
                            rs.getInt("price")),
                    getOrdersRes.getOrderIdx());
            getOrdersRes.setGetOrderItemRes(orderItemList);
        }
        return orderList;
    }

    public int totalOrderPrice(List<PostOrderItemReq> postOrderItemResList) {
        int totalOrderPrice = 0;
        for (PostOrderItemReq orderItem : postOrderItemResList) {
            totalOrderPrice += jdbcTemplate.queryForObject("select price from Product where product_id=?",
                    int.class, orderItem.getProductIdx()) * orderItem.getQuantity();
        }
        return totalOrderPrice;
    }

    public int creteOrderV1(int userIdx) {
        jdbcTemplate.update("insert into Orders (user_id) VALUES (?)", userIdx);
        return this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }

    public void createOrderItems(int lastOrderIdx, List<PostOrderItemReq> postOrderItemResList) {
        for (PostOrderItemReq orderItem : postOrderItemResList) {
            Object[] createOrderItemParams = new Object[]{lastOrderIdx, orderItem.getProductIdx(), orderItem.getQuantity()};
            jdbcTemplate.update("insert into OrderItem (order_id, product_id, quantity) VALUES (?,?,?)", createOrderItemParams);
        }
    }
    public void updateTotalPrice(int totalPrice,int orderIdx) {
        jdbcTemplate.update("update Orders set totalprice=? where order_id=?", totalPrice, orderIdx);
    }

    public void createOrder(int userIdx, List<PostOrderItemReq> postOrderItemResList) throws SQLException {
        Connection c = jdbcTemplate.getDataSource().getConnection();
        c.setAutoCommit(false);
        String createOrderQuery = "insert into Orders (user_id) VALUES (?)";    //ok
        String createOrderItemQuery = "insert into OrderItem (order_id, product_id, quantity) VALUES (?,?,?)";  //ok

        String getProductPriceQuery = "select price from Product where product_id=?";   //ok
        String updateTotalPriceQuery = "update Orders set totalprice=? where order_id=?";   //ok
        String getUserPointQuery = "select point from User where user_id=?";    //ok
        String updateUserMoneyQuery = "update User set point=? where user_id=?";    //ok
        String updateSellerMoneyQuery = "update Seller set point=point+? where seller_id=?";    //ok
        String getSellerIdxQuery = "select seller_id from Product where product_id=?";  //ok

        int totalPrice = 0;
        try {
            Object[] createOrderParams = new Object[]{userIdx};
            jdbcTemplate.update(createOrderQuery, createOrderParams);

            String lastInsertIdQuery = "select last_insert_id()";
            Integer lastOrderIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
            for (PostOrderItemReq orderItem : postOrderItemResList) {
                Object[] createOrderItemParams = new Object[]{lastOrderIdx, orderItem.getProductIdx(), orderItem.getQuantity()};
                jdbcTemplate.update(createOrderItemQuery, createOrderItemParams);
                int priceOrderItems = jdbcTemplate.queryForObject(getProductPriceQuery, int.class, orderItem.getProductIdx()) * orderItem.getQuantity();
                int sellerIxd = jdbcTemplate.queryForObject(getSellerIdxQuery, int.class, orderItem.getProductIdx());
                jdbcTemplate.update(updateSellerMoneyQuery, priceOrderItems, sellerIxd);
                totalPrice += priceOrderItems;
            }
            int userPrice = jdbcTemplate.queryForObject(getUserPointQuery, int.class, userIdx);
            if (userPrice < totalPrice) {
                throw new Exception();
            } else {
                jdbcTemplate.update(updateTotalPriceQuery, totalPrice, lastOrderIdx);
                jdbcTemplate.update(updateUserMoneyQuery, userPrice - totalPrice, userIdx);
            }
            c.commit();
        } catch (Exception e) {
            c.rollback();
        }
        c.close();
    }

    public int cancelOrder(int userIdx, int orderIdx) {
        String cancelOrderQuery = "update Orders set status='CANCEL' where user_id=? and order_id=?";
        Object[] cancelOrderParams = new Object[]{userIdx, orderIdx};
        return this.jdbcTemplate.update(cancelOrderQuery, cancelOrderParams);
    }

    public int modifyOrder(List<PatchOrderReq> patchOrderReqList) {
        String cancelOrderQuery = "update OrderItem set quantity=? where orderitem_id=?";
        int update = 0;
        for (PatchOrderReq patchOrderReq : patchOrderReqList) {
            Object[] cancelOrderParams = new Object[]{patchOrderReq.getQuantity(), patchOrderReq.getOrderItemIdx()};
            update = this.jdbcTemplate.update(cancelOrderQuery, cancelOrderParams);
            if (update == 0) {
                return 0;
            }
        }
        return update;
    }
}
