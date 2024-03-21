package com.write.reco.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserEmailRequest {

    @NotNull
    private String email;
}
