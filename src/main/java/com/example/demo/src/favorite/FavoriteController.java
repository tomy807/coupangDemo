package com.example.demo.src.favorite;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.favorite.model.GetFavoriteRes;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteProvider favoriteProvider;
    private final FavoriteService favoriteService;
    private final JwtService jwtService;

    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetFavoriteRes>> getFavorite(@PathVariable int userIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetFavoriteRes> getFavoriteRes = favoriteProvider.getFavorite(userIdx);
            return new BaseResponse<>(getFavoriteRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("/{userIdx}")
    public BaseResponse<String> createFavorite(@PathVariable int userIdx, @RequestParam int productIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            favoriteService.createFavorite(userIdx, productIdx);
            String result = "찜 목록에 추가했습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PatchMapping("/{userIdx}")
    public BaseResponse<String> deleteFavorite(@PathVariable int userIdx, @RequestParam int productIdx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            favoriteService.deleteFavorite(userIdx,productIdx);
            return new BaseResponse<>("찜 목록에서 삭제했습니다.");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
