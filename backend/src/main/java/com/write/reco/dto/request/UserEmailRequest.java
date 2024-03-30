package com.write.reco.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
public class UserEmailRequest {

    @NotNull
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
}
