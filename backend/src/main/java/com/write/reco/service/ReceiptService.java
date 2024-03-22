package com.write.reco.service;

import com.write.reco.domain.Image;
import com.write.reco.domain.Item;
import com.write.reco.domain.Receipt;
import com.write.reco.dto.request.ReceiptRequest;
import com.write.reco.repository.ItemRepository;
import com.write.reco.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final UserService userService;
    private final ImageService imageService;
    private final ItemRepository itemRepository;

//    public void saveReceipt(User auth, ReceiptRequest receiptRequest) {
//
//        List<Item> list = new ArrayList<>();
//        for (ReceiptRequest.Items o : receiptRequest.getItemList()) {
//            list.add(Item.builder()
//                    .item(o.getItem())
//                    .unitPrice(o.getUnitPrice())
//                    .quantity(o.getQuantity())
//                    .price(o.getPrice())
//                    .build());
//        }
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        Receipt receipt = Receipt.builder()
//                .company(receiptRequest.getCompany())
//                .tradeAt(LocalDate.parse(receiptRequest.getTradeAt(), formatter))
//                .sum(receiptRequest.getSum())
////                .itemList(list)
//                .build();
//
//        receiptRepository.save(receipt);
//    }


    public void saveReceipt(User auth, ReceiptRequest receiptRequest) {

        List<Item> list = new ArrayList<>();
//        for (ReceiptRequest.Items o : receiptRequest.getItemList()) {
//            list.add(Item.builder()
//                    .item(o.getItem())
//                    .unitPrice(o.getUnitPrice())
//                    .quantity(o.getQuantity())
//                    .price(o.getPrice())
//                    .build());
//        }

        for (ReceiptRequest.Items o : receiptRequest.getItemList()) {
            Item item = Item.builder()
                    .item(o.getItem())
                    .unitPrice(o.getUnitPrice())
                    .quantity(o.getQuantity())
                    .price(o.getPrice())
                    .build();
            list.add(
                    item
            );
            itemRepository.save(item);
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String image = receiptRequest.getImage();
        log.info("image={}",image);
        Receipt receipt = Receipt.builder()
                .company(receiptRequest.getCompany())
                .tradeAt(LocalDate.parse(receiptRequest.getTradeAt(), formatter))
                .sum(receiptRequest.getSum())
                .itemList(list)
                .build();

        receiptRepository.save(receipt);
    }


    public String filename(String fullPath) throws MalformedURLException {
        URL url = new URL(fullPath);
        String path = url.getPath();

        return new File(path).getName();
    }
}
