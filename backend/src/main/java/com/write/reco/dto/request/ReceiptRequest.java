package com.write.reco.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptRequest {

    private String image;

    private String company;

    private String tradeAt;

    @Range(min = 0, max = 1000000000)
    private int sum;

    private List<Items> itemList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Items {
        private Long itemId;

        private String item;

        @Range(min = 0, max = 10000000)
        private int unitPrice;

        @Max(999)
        private int quantity;

        @Range(min = 0, max = 100000000)
        private int price;
    }
}
