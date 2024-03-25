package com.write.reco.domain;

import com.write.reco.domain.constant.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

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

    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "receipt", orphanRemoval = true)
    private List<Item> itemList = new ArrayList<>();

    @OneToOne(mappedBy = "receipt", fetch = FetchType.LAZY)
    private Image image;
}
