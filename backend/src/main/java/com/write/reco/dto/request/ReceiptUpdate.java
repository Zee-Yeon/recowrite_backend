package com.write.reco.dto.request;

import jakarta.validation.constraints.Max;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptUpdate {

    @Range(min = 0, max = 1000000000)
    private int sum;

    private List<Items> itemList;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Items {

        private String item;

        @Range(min = 0, max = 10000000)
        private int unitPrice;

        @Max(999)
        private int quantity;

        @Range(min = 0, max = 100000000)
        private int price;
    }
}