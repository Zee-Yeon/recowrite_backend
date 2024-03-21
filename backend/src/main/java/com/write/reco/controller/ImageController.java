package com.write.reco.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.write.reco.advice.response.Response;
import com.write.reco.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.write.reco.advice.response.ResponseCode.SUCCESS_RECEIPT_OCR;


@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@AuthenticationPrincipal User user, @Valid @RequestParam("file")MultipartFile file) throws IOException {
        ResponseEntity<JsonNode> entity = imageService.uploadImage(user, file);
        return new ResponseEntity<>(Response.create(SUCCESS_RECEIPT_OCR, entity), SUCCESS_RECEIPT_OCR.getHttpStatus());
    }
}