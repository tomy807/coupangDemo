package com.example.demo.src.order;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.GetOrdersRes;
import com.example.demo.src.order.model.PatchOrderReq;
import com.example.demo.src.order.model.PostOrderItemReq;
import com.example.demo.src.order.model.PostOrderRes;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProvider orderProvider;
    private final OrderService orderService;
    private final JwtService jwtService;

    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetOrdersRes>> getOrders(@PathVariable int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetOrdersRes> getOrdersRes = orderProvider.getOrders(userIdx);
            return new BaseResponse<>(getOrdersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @PostMapping("/{userIdx}")
    public BaseResponse<String> createOrder(@PathVariable int userIdx, @RequestBody List<PostOrderItemReq> postOrderItemResList) {
        if (postOrderItemResList.isEmpty()) {
            return new BaseResponse<>(POST_ORDER_EMPTY_ORDER);
        }
        for (PostOrderItemReq postOrderItemReq : postOrderItemResList) {
            if (postOrderItemReq.getProductIdx() == null) {
                return new BaseResponse<>(POST_ORDER_EMPTY_PRODUCT);
            }
            if (postOrderItemReq.getQuantity() == null) {
                return new BaseResponse<>(POST_ORDER_EMPTY_QUANTITY);
            }
        }
        try {

            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            String result = "주문이 완료되었습니다.";
            orderService.createOrder(userIdx, postOrderItemResList);

            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("/{userIdx}")
    public BaseResponse<String> cancelOrder(@PathVariable int userIdx, @RequestParam int orderIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            orderService.cancelOrder(userIdx, orderIdx);
            String result="주문 삭제 성공";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("/{userIdx}/modify")
    public BaseResponse<String> modifyOrder(@PathVariable int userIdx, @RequestBody List<PatchOrderReq> patchOrderReqList) {
        if (patchOrderReqList.isEmpty()) {
            return new BaseResponse<>(POST_ORDER_EMPTY_ORDER);
        }
        for (PatchOrderReq patchOrderReq : patchOrderReqList) {
            if (patchOrderReq.getOrderItemIdx() == null) {
                return new BaseResponse<>(PATCH_ORDER_EMPTY_ORDERITEM);
            }
            if (patchOrderReq.getQuantity() == null) {
                return new BaseResponse<>(PATCH_ORDER_EMPTY_QUANTITY);
            }
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            orderService.modifyOrder(patchOrderReqList);
            String result = "수정 성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
