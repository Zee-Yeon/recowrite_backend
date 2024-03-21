package com.write.reco.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ReceiptRequest {
    private String image;
    private String company;
    private String tradeAt;
    private int sum;
    private List<Items> itemList;

    @Getter
    public class Items {
        private String item;
        private int unitPrice;
        private int quantity;
        private int price;
    }
}
