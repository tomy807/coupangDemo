package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.PostAddressReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressDao addressDao;

    public void createAddress(int userIdx, PostAddressReq postAddressReq) throws BaseException {
        try{
            addressDao.createAddress(userIdx, postAddressReq);
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyAddress(int userIdx, PostAddressReq postAddressReq) throws BaseException {
        try{
            addressDao.modifyAddress(userIdx, postAddressReq);
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
