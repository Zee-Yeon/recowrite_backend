package com.write.reco.controller;

import com.write.reco.advice.response.Response;
import com.write.reco.dto.request.UserEmailRequest;
import com.write.reco.dto.request.UserLoginRequest;
import com.write.reco.dto.request.UserRequest;
import com.write.reco.dto.response.JwtResponse;
import com.write.reco.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static com.write.reco.advice.response.ResponseCode.*;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/duplication")
    public ResponseEntity<?> duplication(@RequestBody UserEmailRequest userEmailRequest) {
        userService.duplication(userEmailRequest.getEmail());
        return new ResponseEntity<>(Response.create(AVAILABLE_EMAIL, null), AVAILABLE_EMAIL.getHttpStatus());
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest userRequest) {
        userService.join(userRequest);
        return new ResponseEntity<>(Response.create(SUCCESS_SIGNUP, null), SUCCESS_SIGNUP.getHttpStatus());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
        JwtResponse token = userService.login(userLoginRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccessToken());

        return ResponseEntity.ok().headers(httpHeaders).build();
    }
    @DeleteMapping
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User auth) {
        userService.withdraw(auth);
        return new ResponseEntity<>(Response.create(SUCCESS_DELETE_USER, null), SUCCESS_DELETE_USER.getHttpStatus());
    }
}
