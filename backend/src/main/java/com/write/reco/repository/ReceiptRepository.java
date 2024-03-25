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

    @Query("SELECT r FROM Receipt r JOIN r.itemList i WHERE i.item = :item AND r.image.user.email = :email AND r.status = 'ACTIVE'")
    Page<Receipt> findByItem(@Param("item") String item, @Param("user") String email, Pageable pageable);

    Page<Receipt> findAll(Pageable pageable);
}
