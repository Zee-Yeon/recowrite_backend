package com.write.reco.dto.response;

import com.write.reco.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private String item;

    private int unitPrice;

    private int quantity;

    private int price;

    public static ItemResponse dto(Item item) {
        return ItemResponse.builder()
                .item(item.getItem())
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}
