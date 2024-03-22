package com.write.reco.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

@ToString
@Getter
@AllArgsConstructor
public class UserLoginRequest {

    @NotNull
    @UniqueElements
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotNull
    private String password;
}
