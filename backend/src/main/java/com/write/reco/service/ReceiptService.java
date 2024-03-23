package com.write.reco.service;

import com.write.reco.domain.Image;
import com.write.reco.domain.Item;
import com.write.reco.domain.Receipt;
import com.write.reco.dto.request.ReceiptRequest;
import com.write.reco.dto.response.ReceiptResponse;
import com.write.reco.repository.ItemRepository;
import com.write.reco.repository.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ImageService imageService;
    private final ItemRepository itemRepository;
    private final UserService userService;

    public void saveReceipt(User auth, ReceiptRequest receiptRequest) throws MalformedURLException {

        Image image = filename(receiptRequest.getImage());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Receipt receipt = Receipt.builder()
                .company(receiptRequest.getCompany())
                .tradeAt(LocalDate.parse(receiptRequest.getTradeAt(), formatter))
                .sum(receiptRequest.getSum())
                .build();

        receiptRepository.save(receipt);
        image.setReceipt(receipt);

        for (ReceiptRequest.Items o : receiptRequest.getItemList()) {
            Item item = Item.builder()
                    .receipt(receipt)
                    .item(o.getItem())
                    .unitPrice(o.getUnitPrice())
                    .quantity(o.getQuantity())
                    .price(o.getPrice())
                    .build();
            itemRepository.save(item);
        }
    }

    public Page<ReceiptResponse> searchReceipt(User auth, String company, Integer page) {
        com.write.reco.domain.User user = userService.userDetail(auth);
        String email = user.getEmail();

        Pageable pageable = PageRequest.of(page-1, 10, Sort.Direction.DESC, "company");
        return receiptRepository.findReceiptByImageByAndCompany(email, company, pageable).map(ReceiptResponse::dto);
    }

    private Image filename(String fullPath) throws MalformedURLException {
        URL url = new URL(fullPath);
        String path = url.getPath();
        String filename = new File(path).getName();
        return imageService.checkUploader(filename);
    }
}
