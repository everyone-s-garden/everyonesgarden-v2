package com.garden.back.auth.client.naver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NaverTokenRequest {
    private String grant_type;
    private String client_id;
    private String client_secret;
    private String code;
    private String state;
}
