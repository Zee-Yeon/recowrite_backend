package com.write.reco.repository;

import com.write.reco.domain.Item;
import com.write.reco.domain.Receipt;
import com.write.reco.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("select r from Receipt r where r.image.user = :user and r.company = :company and r.status = 'ACTIVE'")
    Page<Receipt> findByCompany(@Param("user") User user, @Param("company") String company, Pageable pageable);

//    @Query("select r from Receipt r join Item i on r.id = i.receipt.id and r.image.user = :user and i.item = :item and r.status = 'ACTIVE'")
    @Query("select r from Receipt r join Item i where r.image.user = :user and i.item = :item and r.status = 'ACTIVE'")
    Page<Receipt> findByItem(@Param("user") User user, @Param("item") String item, Pageable pageable);

//    @Query("select i from Item i where i.item = :item")
//    List<Item> findTest(@Param("item") String item);
//
//    @Query("select r from Receipt r join Item i on r.id = i.receipt.id and i.item = :item")
//    List<Receipt> findTest2(@Param("item") String item);
}
