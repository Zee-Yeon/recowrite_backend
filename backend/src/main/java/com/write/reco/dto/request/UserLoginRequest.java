package com.write.reco.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class UserLoginRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
