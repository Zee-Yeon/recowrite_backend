package com.write.reco.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

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
        private Long itemId;
        private String item;
        private int unitPrice;
        private int quantity;
        private int price;
    }
}
