package com.example.demo.src.address;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.address.model.GetAddressRes;
import com.example.demo.src.address.model.PostAddressReq;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressProvider addressProvider;
    private final AddressService addressService;
    private final JwtService jwtService;

    @GetMapping("{userIdx}")
    public BaseResponse<GetAddressRes> getAddress(@PathVariable int userIdx) {
        try {
            GetAddressRes getAddressRes = addressProvider.getAddress(userIdx);
            return new BaseResponse<>(getAddressRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("{userIdx}")
    public BaseResponse<String> createAddress(@PathVariable int userIdx, @RequestBody PostAddressReq postAddressReq) {
        if (postAddressReq.getMainAddress() == null) {
            return new BaseResponse<>(POST_ADDRESS_EMPTY_MAIN_ADDRESS);
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            addressService.createAddress(userIdx, postAddressReq);
            String result = "주소가 생성됐습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("{userIdx}")
    public BaseResponse<String> modifyAddress(@PathVariable int userIdx, @RequestBody PostAddressReq postAddressReq) {
        if (postAddressReq.getMainAddress() == null) {
            return new BaseResponse<>(PATCH_ADDRESS_EMPTY_MAIN_ADDRESS);
        }
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            addressService.modifyAddress(userIdx, postAddressReq);
            String result = "주소가 수정됐습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
