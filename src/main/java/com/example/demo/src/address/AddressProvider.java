package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.src.address.model.GetAddressRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class AddressProvider {

    private final AddressDao addressDao;

    public GetAddressRes getAddress(int userIdx) throws BaseException {

        try{
            GetAddressRes getAddressRes = addressDao.getAddress(userIdx);

            return getAddressRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
