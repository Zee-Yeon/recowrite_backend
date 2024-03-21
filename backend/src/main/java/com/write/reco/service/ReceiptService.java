package com.write.reco.service;

import com.write.reco.domain.Image;
import com.write.reco.domain.Item;
import com.write.reco.domain.Receipt;
import com.write.reco.dto.request.ReceiptRequest;
import com.write.reco.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptService {

    private final Environment environment;
    private final ReceiptRepository receiptRepository;
    private final UserService userService;

    public void saveReceipt(User auth, ReceiptRequest receiptRequest) {

        com.write.reco.domain.User user = userService.userDetail(auth);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Item> list = new ArrayList<>();
        for (ReceiptRequest.Items o : receiptRequest.getItemList()) {
            list.add(Item.builder()
                    .item(o.getItem())
                    .unitPrice(o.getUnitPrice())
                    .quantity(o.getQuantity())
                    .price(o.getPrice())
                    .build());
        }

        Receipt receipt = Receipt.builder()
                .company(receiptRequest.getCompany())
                .tradeAt(LocalDateTime.parse(receiptRequest.getTradeAt(), formatter))
                .sum(receiptRequest.getSum())
                .itemList(list)
                .build();

        receiptRepository.save(receipt);
    }
}
