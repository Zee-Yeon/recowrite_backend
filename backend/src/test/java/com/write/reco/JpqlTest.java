package com.write.reco;

import com.write.reco.domain.Item;
import com.write.reco.domain.Receipt;
import com.write.reco.repository.ReceiptRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JpqlTest {
    @Autowired
    private ReceiptRepository receiptRepository;

//    @Test
//    public void test01() {
//        List<Item> items = receiptRepository.findTest("안경");
//
//        items.forEach(i -> System.out.println(i.getItem()));
//    }

//    @Test
//    public void test02() {
//        List<Receipt> test2 = receiptRepository.findTest2("안경");
//
//        test2.forEach(i -> System.out.println(i.getId()));
//    }

}
