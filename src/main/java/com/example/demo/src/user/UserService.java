package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.oauth.AccessToken;
import com.example.demo.src.oauth.OAuthService;
import com.example.demo.src.oauth.ProfileDto;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;
    private final OAuthService oAuthService;

    @Value("${secret.user_info_password_key}")
    private String userInfoPasswordKey;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService, OAuthService oAuthService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
        this.oAuthService = oAuthService;
    }

    @Transactional
    public PostUserRes loginMemberByProvider(String code, String provider) throws BaseException {
        try {
            AccessToken accessToken = oAuthService.getAccessToken(code, provider);
            ProfileDto profile = oAuthService.getProfile(accessToken.getAccess_token(), provider);

            List<GetUserRes> user = userDao.findByEmailAndProvider(profile.getEmail(), provider);
            if (!user.isEmpty()) {
                GetUserRes findUser = user.get(0);
                return new PostUserRes(jwtService.createJwt(findUser.getUserIdx()), findUser.getUserIdx());
            } else {
                int userIdx = userDao.createUserOAuth(profile, provider);
                return new PostUserRes(jwtService.createJwt(userIdx), userIdx);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try{
            //암호화
            pwd = new AES128(userInfoPasswordKey).encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updatePoint(int totalOrderPrice, int userIdx) throws BaseException {
        try{
            userDao.updatePoint(totalOrderPrice, userIdx);

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateSellerPoint(int point, int sellerIdx) throws BaseException {
        try{
            userDao.updateSellerPoint(point, sellerIdx);

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void withdrawal(int userIdx) throws BaseException {
        try{
            int result = userDao.withdrawal(userIdx);
            if(result == 0){
                throw new BaseException(WITHDRAWAL_FAIL_USER);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
