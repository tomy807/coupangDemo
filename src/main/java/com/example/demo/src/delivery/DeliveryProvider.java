package com.example.demo.src.delivery;


import com.example.demo.config.BaseException;
import com.example.demo.src.delivery.model.GetDeliveryItemRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class DeliveryProvider {

    private final DeliveryDao deliveryDao;

    public List<GetDeliveryItemRes> getItemsByRocket() throws BaseException {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryDao.getItemsByRocket();
            return getDeliveryItemRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetDeliveryItemRes> getItemsByRocketByLargeCategory(int categoryLargeIdx) throws BaseException {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryDao.getItemsByRocketByLargeCategory(categoryLargeIdx);
            return getDeliveryItemRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetDeliveryItemRes> getItemsByRocketByMiddleCategory(int categoryLargeIdx,int categoryMiddleIdx) throws BaseException {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryDao.getItemsByRocketByMiddleCategory(categoryLargeIdx,categoryMiddleIdx);
            return getDeliveryItemRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetDeliveryItemRes> getItemsByRocketBySmallCategory(int categoryLargeIdx,int categoryMiddleIdx,int categorySmallIdx) throws BaseException {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryDao.getItemsByRocketBySmallCategory(categoryLargeIdx,categoryMiddleIdx,categorySmallIdx);
            return getDeliveryItemRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetDeliveryItemRes> getItemsByFresh() throws BaseException {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryDao.getItemsByFresh();
            return getDeliveryItemRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetDeliveryItemRes> getItemsByCrossBorder() throws BaseException {
        try {
            List<GetDeliveryItemRes> getDeliveryItemRes = deliveryDao.getItemsByCrossBorder();
            return getDeliveryItemRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
