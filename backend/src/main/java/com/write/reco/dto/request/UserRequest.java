package com.write.reco.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String company;

    @NotNull
    private String name;
}
