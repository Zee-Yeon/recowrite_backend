package com.write.reco.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Receipt extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long id;

    private LocalDate tradeAt;

    private String company;

    private int sum;

    @OneToMany(mappedBy = "receipt", orphanRemoval = true)
    private List<Item> itemList = new ArrayList<>();

    @OneToOne(mappedBy = "receipt", fetch = FetchType.LAZY)
    private Image image;
}
