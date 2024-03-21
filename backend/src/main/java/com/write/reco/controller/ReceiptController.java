package com.write.reco.controller;

import com.write.reco.advice.response.Response;
import com.write.reco.dto.request.ReceiptRequest;
import com.write.reco.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.write.reco.advice.response.ResponseCode.SUCCESS_SAVE_RECEIPT;


@RestController
@RequiredArgsConstructor
@RequestMapping("/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<?> saveReceipt(@AuthenticationPrincipal User auth, @RequestBody ReceiptRequest receiptRequest) {
        receiptService.saveReceipt(auth, receiptRequest);
        return new ResponseEntity<>(Response.create(SUCCESS_SAVE_RECEIPT, null), SUCCESS_SAVE_RECEIPT.getHttpStatus());
    }
}
