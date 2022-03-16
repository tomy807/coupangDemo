package com.example.demo.src.favorite;


import com.example.demo.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteDao favoriteDao;

    public void createFavorite(int userIdx, int productIdx) throws BaseException {
        try {
            favoriteDao.createFavorite(userIdx, productIdx);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteFavorite(int userIdx, int productIdx) throws BaseException {
        try {
            favoriteDao.deleteFavorite(userIdx, productIdx);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

