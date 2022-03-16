package com.example.demo.src.order;


import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.PatchOrderReq;
import com.example.demo.src.order.model.PostOrderItemReq;
import com.example.demo.src.product.ProductProvider;
import com.example.demo.src.product.ProductService;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderDao orderDao;
    private final UserProvider userProvider;
    private final UserService userService;
    private final ProductProvider productProvider;
    private final ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(int userIdx, List<PostOrderItemReq> postOrderItemResList) throws BaseException {

        int totalOrderPrice = 0;
        try {
            // 주문 가능한 돈 가졌는지 확인
            totalOrderPrice = orderDao.totalOrderPrice(postOrderItemResList);
            int userPoint = userProvider.getPoint(userIdx);
            if (totalOrderPrice > userPoint) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new BaseException(USERS_NOT_ENOUGH_POINT);
        }
        try {
            //주문영역
            int orderIdx = orderDao.creteOrderV1(userIdx);
            orderDao.updateTotalPrice(totalOrderPrice, orderIdx);
            orderDao.createOrderItems(orderIdx, postOrderItemResList);

            //계산영역
            //판매자들 계산(+)
            for (PostOrderItemReq orderItem : postOrderItemResList) {
                int sellerIdx = userProvider.getSellerIdx(orderItem.getProductIdx());
                int productPrice = productProvider.getPrice(orderItem.getProductIdx());
                userService.updateSellerPoint(productPrice * orderItem.getQuantity(), sellerIdx);
            }

            //구매자 계산(-)
            userService.updatePoint(totalOrderPrice, userIdx);

            //Product stock 감소 영역
            for (PostOrderItemReq orderItem : postOrderItemResList) {
                productService.updateProductStock(orderItem.getQuantity(), orderItem.getProductIdx());
            }

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void cancelOrder(int userIdx, int orderIdx) throws BaseException {
        try {
            int result = orderDao.cancelOrder(userIdx, orderIdx);
            if (result == 0) {
                throw new BaseException(CANCEL_ORDER_FAIL);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyOrder(List<PatchOrderReq> patchOrderReq) throws BaseException {
        try {
            int result = orderDao.modifyOrder(patchOrderReq);
            if (result == 0) {
                throw new BaseException(CANCEL_MODIFY_FAIL);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
