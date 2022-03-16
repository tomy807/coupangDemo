package com.example.demo.src.recent;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.recent.model.GetRecentRes;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/recent-product")
@RequiredArgsConstructor
public class RecentController {

    private final JwtService jwtService;
    private final RecentProvider recentProvider;

    @GetMapping("{userIdx}")
    public BaseResponse<List<GetRecentRes>> getRecent(@PathVariable int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetRecentRes> getOrdersRes = recentProvider.getRecent(userIdx);
            return new BaseResponse<>(getOrdersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
