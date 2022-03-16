package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.PatchProductReq;
import com.example.demo.src.product.model.PatchProductRes;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public PostProductRes createProduct(PostProductReq postProductReq) throws BaseException {
        try {
            int product = productDao.createProduct(postProductReq);
            return new PostProductRes(product);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PatchProductRes modifyProduct(PatchProductReq patchProductReq) throws BaseException {
        try {
            PatchProductRes patchProductRes = productDao.modifyProduct(patchProductReq);
            return patchProductRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateProductStock(int quantity, int productIdx) throws BaseException {
        try {
            productDao.updateProductStock(quantity, productIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
