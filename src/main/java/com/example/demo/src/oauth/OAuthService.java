package com.example.demo.src.oauth;


import com.example.demo.config.BaseException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.naming.CommunicationException;

import static com.example.demo.config.BaseResponseStatus.RESPONSE_ERROR;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

    private final OAuthRequestFactory oAuthRequestFactory;
    private final RestTemplate restTemplate;
    private final Gson gson;



    public ProfileDto getProfile(String accessToken, String provider) throws BaseException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String profileUrl = oAuthRequestFactory.getProfileUrl(provider);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);

        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return extractProfile(response, provider);
            }
        } catch (Exception e) {
            throw new BaseException(RESPONSE_ERROR);
        }
        throw new BaseException(RESPONSE_ERROR);
    }

    private ProfileDto extractProfile(ResponseEntity<String> response, String provider) {
        if (provider.equals("kakao")) {
            KakaoProfile kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
            return new ProfileDto(kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getKakao_account().getProfile().getNickname(), kakaoProfile.getKakao_account().getProfile().getImage());
        } else if(provider.equals("google")) {
            GoogleProfile googleProfile = gson.fromJson(response.getBody(), GoogleProfile.class);
            return new ProfileDto(googleProfile.getEmail(), googleProfile.getName(), googleProfile.getImageUrl());
        } else {
            NaverProfile naverProfile = gson.fromJson(response.getBody(), NaverProfile.class);
            return new ProfileDto(naverProfile.getResponse().getEmail(), naverProfile.getResponse().getName(), naverProfile.getResponse().getImageUrl());
        }
    }
    public AccessToken getAccessToken(String code, String provider) throws BaseException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        OAuthRequest oAuthRequest = oAuthRequestFactory.getRequest(code, provider);
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(oAuthRequest.getMap(), httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(oAuthRequest.getUrl(), request, String.class);
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                AccessToken accessToken = gson.fromJson(response.getBody(), AccessToken.class);
                System.out.println("Access_token : "+accessToken.getAccess_token());
                System.out.println("Token_type : "+accessToken.getToken_type());
                System.out.println("Refresh_token : "+accessToken.getRefresh_token());
                System.out.println("Expires_in : "+accessToken.getExpires_in());
                System.out.println("Refresh_token_expires_in : "+accessToken.getRefresh_token_expires_in());
                return accessToken;
            }
        } catch (Exception e) {
            throw new BaseException(RESPONSE_ERROR);
        }
        throw new BaseException(RESPONSE_ERROR);
    }
}
