package com.example.demo.src.oauth;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;

@Getter
@AllArgsConstructor
public class OAuthRequest {
    private String url;
    private LinkedMultiValueMap<String, String> map;
}
