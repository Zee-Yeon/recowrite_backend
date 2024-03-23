package com.write.reco.controller;

import com.write.reco.advice.response.Response;
import com.write.reco.domain.Receipt;
import com.write.reco.dto.request.ReceiptRequest;
import com.write.reco.dto.response.ReceiptResponse;
import com.write.reco.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

import static com.write.reco.advice.response.ResponseCode.GET_RECEIPTS;
import static com.write.reco.advice.response.ResponseCode.SUCCESS_SAVE_RECEIPT;


@RestController
@RequiredArgsConstructor
@RequestMapping("/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

//    @PostMapping("/save")
//    public ResponseEntity<?> saveReceipt(@AuthenticationPrincipal User auth, @RequestBody ReceiptRequest receiptRequest) {
//        receiptService.saveReceipt(auth, receiptRequest);
//        return new ResponseEntity<>(Response.create(SUCCESS_SAVE_RECEIPT, null), SUCCESS_SAVE_RECEIPT.getHttpStatus());
//    }

    @PostMapping("/save")
    public ResponseEntity<?> saveReceipt(@AuthenticationPrincipal User auth, @RequestBody ReceiptRequest request) throws MalformedURLException {
        receiptService.saveReceipt(auth, request);
        return new ResponseEntity<>(Response.create(SUCCESS_SAVE_RECEIPT, null), SUCCESS_SAVE_RECEIPT.getHttpStatus());
    }

    // 업체명 (물품명, 거래기간)
    @GetMapping
    public ResponseEntity<?> searchReceipt(@AuthenticationPrincipal User auth,
                                           @RequestParam(required = false) String company,
                                           @RequestParam(required = false, defaultValue = "1", value = "page") Integer page) {
        Page<ReceiptResponse> receipt = receiptService.searchReceipt(auth, company, page);

        return new ResponseEntity<>(Response.create(GET_RECEIPTS, receipt), GET_RECEIPTS.getHttpStatus());
    }

//    @PostMapping("/test")
//    public ResponseEntity<?> test(@RequestParam("path") String path) throws MalformedURLException {
//        String filename = receiptService.filename(path);
//        return new ResponseEntity<>(Response.create(SUCCESS_SAVE_RECEIPT, filename), SUCCESS_SAVE_RECEIPT.getHttpStatus());
//    }
}
