package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.entity.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    void updateTest(){
        Book book = em.find(Book.class, 1L);


        // @Transactional
        book.setName("asdffq");

        // 변경 감지 == dirty checking
        // @Transactional commit

    }
}
