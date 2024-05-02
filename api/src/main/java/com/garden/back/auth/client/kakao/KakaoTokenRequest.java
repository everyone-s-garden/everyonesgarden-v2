package com.garden.back.auth.client.kakao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KakaoTokenRequest{
    String grant_type;
    String client_id;
    String redirect_uri;
    String code;
}
