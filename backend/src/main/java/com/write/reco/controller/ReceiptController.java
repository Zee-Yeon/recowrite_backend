package com.write.reco.controller;

import com.write.reco.advice.response.Response;
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

import static com.write.reco.advice.response.ResponseCode.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

    @PostMapping("/save")
    public ResponseEntity<?> saveReceipt(@AuthenticationPrincipal User auth, @RequestBody ReceiptRequest request) throws MalformedURLException {
        receiptService.saveReceipt(auth, request);
        return new ResponseEntity<>(Response.create(SUCCESS_SAVE_RECEIPT, null), SUCCESS_SAVE_RECEIPT.getHttpStatus());
    }

    // 업체명
//    @GetMapping
//    public ResponseEntity<?> searchReceipt(@AuthenticationPrincipal User auth,
//                                           @RequestParam(required = false) String company,
//                                           @RequestParam(required = false, defaultValue = "1", value = "page") Integer page) {
//        Page<ReceiptResponse> receipt = receiptService.searchReceipt(auth, company, page);
//
//        return new ResponseEntity<>(Response.create(GET_RECEIPTS, receipt), GET_RECEIPTS.getHttpStatus());
//    }

    // 물품명
    @GetMapping
    public ResponseEntity<?> searchReceipt(@AuthenticationPrincipal User auth,
                                           @RequestParam(required = false) String item,
                                           @RequestParam(required = false, defaultValue = "1", value = "page") Integer page) {
        Page<ReceiptResponse> receipt = receiptService.searchReceipt(auth, item, page);

        return new ResponseEntity<>(Response.create(GET_RECEIPTS, receipt), GET_RECEIPTS.getHttpStatus());
    }

    // 영수증 상세보기
//    @GetMapping("/{receiptId}")
//    public ResponseEntity<?> getReceipt(@PathVariable("receiptId") Long receiptId) {
//        ReceiptResponse receipt = receiptService.getReceipt(receiptId);
//        return new new ResponseEntity<>(Response.create(GET_RECEIPT, receipt), GET_RECEIPT.getHttpStatus());
//    }
}
