package com.example.demo.src.order;


import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.GetOrdersRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class OrderProvider {
    private final OrderDao orderDao;

    public List<GetOrdersRes> getOrders(int userIdx) throws BaseException {
        try {
            List<GetOrdersRes> getUserRes = orderDao.getOrders(userIdx);
            return getUserRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
