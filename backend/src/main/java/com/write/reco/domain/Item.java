package com.write.reco.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Setter
    private String item;

    @Setter
    private int unitPrice;

    @Setter
    private int quantity;

    @Setter
    private int price;

    @Setter
    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
}
