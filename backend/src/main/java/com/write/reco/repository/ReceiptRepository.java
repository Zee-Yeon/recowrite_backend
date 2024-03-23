package com.write.reco.repository;

import com.write.reco.domain.Item;
import com.write.reco.domain.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("select r from Receipt r where r.image.user.email = :email and r.company = :company")
    Page<Receipt> findReceiptByImageByAndCompany(String email, String company, Pageable pageable);

}
