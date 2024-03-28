package com.write.reco.repository;

import com.write.reco.domain.Item;
import com.write.reco.domain.Receipt;
import com.write.reco.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("SELECT r FROM Receipt r WHERE r.image.user = :user AND r.company = :company AND r.status = 'ACTIVE'")
    Page<Receipt> findByCompany(@Param("user") User user, @Param("company") String company, Pageable pageable);

    @Query("SELECT r FROM Receipt r JOIN r.itemList i WHERE i.item LIKE %:item% AND r.image.user = :user AND r.status = 'ACTIVE'")
    Page<Receipt> findByItem(@Param("item") String item, @Param("user") User user, Pageable pageable);

    @Query("SELECT r FROM Receipt r WHERE r.image.user = :user AND r.status = 'ACTIVE'")
    Page<Receipt> findByAll(@Param("user") User user, Pageable pageable);

    @Query("SELECT r FROM Receipt r WHERE r.image.user = :user AND r.tradeAt BETWEEN :start AND :end AND r.status = 'ACTIVE'")
    Page<Receipt> findByDate(@Param("user") User user, @Param("start") LocalDate start,  @Param("end") LocalDate end, Pageable pageable);

    @Query("SELECT r FROM Receipt r WHERE r.image.user = :user AND r.company = :company AND r.tradeAt BETWEEN :start AND :end AND r.status = 'ACTIVE'")
    Page<Receipt> findByCompanyAndDate(@Param("user") User user, @Param("company") String company, @Param("start") LocalDate start,  @Param("end") LocalDate end, Pageable pageable);

    @Query("SELECT r FROM Receipt r JOIN r.itemList i WHERE i.item LIKE %:item% AND r.image.user = :user AND r.tradeAt BETWEEN :start AND :end AND r.status = 'ACTIVE'")
    Page<Receipt> findByItemAndDate(@Param("user") User user, @Param("item") String item, @Param("start") LocalDate start,  @Param("end") LocalDate end, Pageable pageable);
}
