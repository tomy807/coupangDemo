package com.example.demo.src.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoProfile {
    KakaoAccount kakao_account;

    @Data
    public class KakaoAccount {
        private Profile profile;
        private String email;

        @Data
        public class Profile {
            private String image;
            private String nickname;
        }
    }
}
