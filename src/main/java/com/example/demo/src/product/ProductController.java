package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.*;
import com.example.demo.src.review.model.GetProductReviewsRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductProvider productProvider;

    private final ProductService productService;

    @PostMapping("")
    public BaseResponse<PostProductRes> createProduct(@RequestBody PostProductReq postProductReq) {
        if (postProductReq.getDeliveryIdx() == null) {
            return new BaseResponse<>(POST_PRODUCT_EMPTY_DELIVERY);
        }
        if (postProductReq.getSellerIdx() == null) {
            return new BaseResponse<>(POST_PRODUCT_EMPTY_SELLER);
        }
        if (postProductReq.getItemIdx() == null) {
            return new BaseResponse<>(POST_PRODUCT_EMPTY_ITEM);
        }
        if (postProductReq.getPrice() == null) {
            return new BaseResponse<>(POST_PRODUCT_EMPTY_PRICE);
        }
        if (postProductReq.getDiscount() == null) {
            return new BaseResponse<>(POST_PRODUCT_EMPTY_DISCOUNT);
        }
        if (postProductReq.getMembershipDiscount() == null) {
            return new BaseResponse<>(POST_PRODUCT_EMPTY_MEMBERSHIP_DISCOUNT);
        }
        if (postProductReq.getStock() == null) {
            return new BaseResponse<>(POST_PRODUCT_EMPTY_STOCK);
        }
        try {
            PostProductRes postProductRes = productService.createProduct(postProductReq);
            return new BaseResponse<>(postProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/{productIdx}")
    public BaseResponse<GetProductRes> getProduct(@PathVariable int productIdx) {
        try {
            GetProductRes getProductRes = productProvider.getProduct(productIdx);
            return new BaseResponse<>(getProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("/{productIdx}")
    public BaseResponse<PatchProductRes> modifyProduct(@PathVariable int productIdx, @RequestBody Product product) {
        try {

            PatchProductReq patchProductReq = new PatchProductReq(productIdx,
                    product.getProductImgUrl(),
                    product.getPrice(),
                    product.getDiscount(),
                    product.getMembershipDiscount(),
                    product.getPrice(),
                    product.getProductStatus());
            PatchProductRes patchProductRes = productService.modifyProduct(patchProductReq);
            return new BaseResponse<>(patchProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
