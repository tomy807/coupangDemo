package com.example.demo.src.delivery;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.delivery.model.GetDeliveryItemRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryProvider deliveryProvider;

    @GetMapping("/rocket")
    public BaseResponse<List<GetDeliveryItemRes>> getItemsByRocket() {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryProvider.getItemsByRocket();
            return new BaseResponse<>(getDeliveryItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/rocket/categories/{categoryLargeIdx}")
    public BaseResponse<List<GetDeliveryItemRes>> getItemsByRocketByLargeCategory(@PathVariable int categoryLargeIdx) {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryProvider.getItemsByRocketByLargeCategory(categoryLargeIdx);
            return new BaseResponse<>(getDeliveryItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/rocket/categories/{categoryLargeIdx}/{categoryMiddleIdx}")
    public BaseResponse<List<GetDeliveryItemRes>> getItemsByRocketByMiddleCategory(@PathVariable int categoryLargeIdx,@PathVariable int categoryMiddleIdx) {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryProvider.getItemsByRocketByMiddleCategory(categoryLargeIdx,categoryMiddleIdx);
            return new BaseResponse<>(getDeliveryItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/rocket/categories/{categoryLargeIdx}/{categoryMiddleIdx}/{categorySmallIdx}")
    public BaseResponse<List<GetDeliveryItemRes>> getItemsByRocketBySmallCategory(@PathVariable int categoryLargeIdx, @PathVariable int categoryMiddleIdx, @PathVariable int categorySmallIdx) {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryProvider.getItemsByRocketBySmallCategory(categoryLargeIdx,categoryMiddleIdx,categorySmallIdx);
            return new BaseResponse<>(getDeliveryItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/fresh")
    public BaseResponse<List<GetDeliveryItemRes>> getItemsByFresh() {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryProvider.getItemsByFresh();
            return new BaseResponse<>(getDeliveryItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @GetMapping("/crossborder")
    public BaseResponse<List<GetDeliveryItemRes>> getItemsByCrossBorder() {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryProvider.getItemsByCrossBorder();
            return new BaseResponse<>(getDeliveryItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
