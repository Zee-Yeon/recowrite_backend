package com.write.reco.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
    private String accessToken;

    public static JwtResponse create(String accessToken) {
        return JwtResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
