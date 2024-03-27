package com.write.reco.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptUpdate {

    private int sum;

    private List<Items> itemList;

    @Getter
    public static class Items {
        private String item;
        private int unitPrice;
        private int quantity;
        private int price;
    }
}