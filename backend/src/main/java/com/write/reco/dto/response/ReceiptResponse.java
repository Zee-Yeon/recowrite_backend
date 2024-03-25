package com.write.reco.dto.response;

import com.write.reco.domain.Receipt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponse {
    private Long receiptId;

    private String company;

    private String tradAt;

    private int sum;

    private List<ItemResponse> itemList;

    public static ReceiptResponse dto(Receipt receipt) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return ReceiptResponse.builder()
                .receiptId(receipt.getId())
                .company(receipt.getCompany())
                .tradAt(receipt.getTradeAt().format(format))
                .sum(receipt.getSum())
                .itemList(receipt.getItemList().stream().map(ItemResponse::dto).collect(Collectors.toList()))
                .build();
    }
}
