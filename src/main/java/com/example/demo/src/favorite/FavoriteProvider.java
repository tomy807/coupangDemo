package com.example.demo.src.favorite;


import com.example.demo.config.BaseException;
import com.example.demo.src.favorite.model.GetFavoriteRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class FavoriteProvider {

    private final FavoriteDao favoriteDao;

    public List<GetFavoriteRes> getFavorite(int userIdx) throws BaseException {
        try {
            List<GetFavoriteRes> getFavoriteRes = favoriteDao.getFavorite(userIdx);
            return getFavoriteRes;
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
