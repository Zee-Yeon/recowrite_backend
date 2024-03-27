package com.write.reco.service;

import com.write.reco.advice.exception.CustomException;
import com.write.reco.advice.response.ResponseCode;
import com.write.reco.domain.Image;
import com.write.reco.domain.Item;
import com.write.reco.domain.Receipt;
import com.write.reco.domain.constant.Status;
import com.write.reco.dto.request.ReceiptRequest;
import com.write.reco.dto.request.ReceiptUpdate;
import com.write.reco.dto.response.ReceiptResponse;
import com.write.reco.repository.ItemRepository;
import com.write.reco.repository.ReceiptRepository;
import jakarta.persistence.EntityManager;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
                .status(Status.ACTIVE)
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

        Pageable pageable = PageRequest.of(page-1, 10, Sort.Direction.DESC, "company");
        return receiptRepository.findByCompany(user, company, pageable).map(ReceiptResponse::dto);
    }

    public Page<ReceiptResponse> searchReceipt2(User auth, String item, Integer page) {
        com.write.reco.domain.User user = userService.userDetail(auth);

        Pageable pageable = PageRequest.of(page-1, 10, Sort.Direction.DESC, "id");
        return receiptRepository.findByItem(item, user, pageable).map(ReceiptResponse::dto);
    }

    public Page<ReceiptResponse> searchReceipt3(User auth, String start, String end, Integer page) {
        com.write.reco.domain.User user = userService.userDetail(auth);

        LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Pageable pageable = PageRequest.of(page-1, 10, Sort.Direction.DESC, "id");
        return receiptRepository.findByDate(user, startDate, endDate, pageable).map(ReceiptResponse::dto);
    }

    public Page<ReceiptResponse> searchReceipt4(User auth, Integer page) {
        com.write.reco.domain.User user = userService.userDetail(auth);

        Pageable pageable = PageRequest.of(page-1, 10, Sort.Direction.DESC, "id");
        return receiptRepository.findByAll(user, pageable).map(ReceiptResponse::dto);
    }

    public ReceiptResponse getReceipt(Long receiptId) {
        Receipt receipt = findByReceiptId(receiptId);
        return ReceiptResponse.dto(receipt);
    }

//    public ReceiptResponse updateReceipt(Long receiptId, ReceiptUpdate receiptUpdate) {
//        Receipt receipt = findByReceiptId(receiptId);
//        receipt.setSum(receiptUpdate.getSum());
//
//
//
//        List<Item> updatedItems = new ArrayList<>();
//        for (ReceiptUpdate.Items updateItem : receiptUpdate.getItemList()) {
//            Item o = new Item();
//            o.setItem(updateItem.getItem());
//            o.setUnitPrice(updateItem.getUnitPrice());
//            o.setQuantity(updateItem.getQuantity());
//            o.setPrice(updateItem.getPrice());
//            o.setReceipt(receipt);
//            itemRepository.save(o);
//
//            updatedItems.add(o);
//        }
//        System.out.println("updatedItems = " + updatedItems.get(0).getItem());
//        receipt.setItemList(updatedItems);
//
//        Receipt save = receiptRepository.save(receipt);
//        ReceiptResponse receiptEdit = ReceiptResponse.dto(save);
//
//        return receiptEdit;
//    }

    public ReceiptResponse updateReceipt(Long receiptId, ReceiptUpdate receiptUpdate) {
        Receipt receipt = findByReceiptId(receiptId);
        receipt.setSum(receiptUpdate.getSum());

        receipt.getItemList().forEach(item -> itemRepository.deleteById(item.getId()));
        List<Item> updatedItems = new ArrayList<>();

        if (receiptUpdate.getItemList() != null) {
            for (ReceiptUpdate.Items updateItem : receiptUpdate.getItemList()) {
                Item o = new Item();
                o.setItem(updateItem.getItem());
                o.setUnitPrice(updateItem.getUnitPrice());
                o.setQuantity(updateItem.getQuantity());
                o.setPrice(updateItem.getPrice());
                o.setReceipt(receipt);
                itemRepository.save(o);

                updatedItems.add(o);
            }
        }
        System.out.println("updatedItems = " + updatedItems.get(0).getItem());
        receipt.setItemList(updatedItems);
        Receipt save = receiptRepository.save(receipt);

        return ReceiptResponse.dto(save);
    }

    public void deleteReceipt(User auth, Long receiptId) {
        Receipt receipt = findByReceiptId(receiptId);
        receipt.setStatus(Status.DELETED);
    }

    private Image filename(String fullPath) throws MalformedURLException {
        URL url = new URL(fullPath);
        String path = url.getPath();
        String filename = new File(path).getName();
        return imageService.checkUploader(filename);
    }

    private Receipt findByReceiptId(Long receiptId) {
        return receiptRepository.findById(receiptId)
                .orElseThrow(() -> new CustomException(ResponseCode.NOT_FOUND_RECEIPT));
    }
}
