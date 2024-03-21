package com.write.reco.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.write.reco.advice.exception.CustomException;
import com.write.reco.advice.response.ResponseCode;
import com.write.reco.domain.Image;
import com.write.reco.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private static final String FLASK_PREDICT_URL = "http://10.125.121.181:5000/image/upload" ;
    private final Environment environment;
    private final UserService userService;

    public ResponseEntity<JsonNode> uploadImage(User auth, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new CustomException(ResponseCode.NOT_FOUND_FILE);
        }

        com.write.reco.domain.User user = userService.userDetail(auth);
        System.out.println("user = " + user.getEmail());

        String directoryPath = environment.getProperty("file.dir");
        String filename = filename(file);
        String fullPath = directoryPath + filename;

        file.transferTo(new File(fullPath));

        return sendImage(fullPath);
    }

    // 1. UUID 사용한 파일명
    private String filename(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        return uuid + "_" + file.getOriginalFilename();
    }

    // 2. flask 영수증 이미지 전송 -> front 전송
    private ResponseEntity<JsonNode> sendImage(String fullPath) {

        RestTemplate restTemplate = new RestTemplate();
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        Resource resource = new FileSystemResource(fullPath);
        body.add("image", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<LinkedMultiValueMap<String, Object>> http = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> entity = restTemplate.postForEntity(FLASK_PREDICT_URL, http, JsonNode.class);

        return entity;
    }
}

