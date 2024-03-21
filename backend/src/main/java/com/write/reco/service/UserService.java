package com.write.reco.service;

import com.write.reco.advice.exception.CustomException;
import com.write.reco.advice.response.ResponseCode;
import com.write.reco.domain.User;
import com.write.reco.domain.constant.Status;
import com.write.reco.dto.request.UserEmailRequest;
import com.write.reco.dto.request.UserLoginRequest;
import com.write.reco.dto.request.UserRequest;
import com.write.reco.dto.response.JwtResponse;
import com.write.reco.repository.UserRepository;
import com.write.reco.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    public void join(UserRequest userRequest) {
        duplication(userRequest.getEmail());

        User user = User.builder()
                .email(userRequest.getEmail())
                .password(encoder.encode(userRequest.getPassword()))
                .name(userRequest.getName())
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public JwtResponse login(UserLoginRequest userLoginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtResponse token = jwtProvider.createToken(authentication);
        return token;
    }


    public void duplication(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new CustomException(ResponseCode.EMAIL_ALREADY_EXIST);
                });
    }

    public User userDetail(org.springframework.security.core.userdetails.User auth) {
        String email = auth.getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_USER));
    }

    public void withdraw(org.springframework.security.core.userdetails.User auth) {
        User user = userDetail(auth);
//        user.
    }
}
