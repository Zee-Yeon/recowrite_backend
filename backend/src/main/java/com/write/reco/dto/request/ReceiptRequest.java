package com.write.reco.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptRequest {

    private String image;

    private String company;

    private String tradeAt;

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
