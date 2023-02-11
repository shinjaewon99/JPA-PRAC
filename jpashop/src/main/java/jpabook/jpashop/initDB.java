package jpabook.jpashop;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.entity.Delivery;
import jpabook.jpashop.domain.entity.Member;
import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.domain.entity.OrderItem;
import jpabook.jpashop.domain.entity.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 총 주문 2개
 * * Shin
 *    * JPA1 BOOK
 *    * JPA2 BOOK
 *
 * * KIM
 *    * Spring1 BOOK
 *    * Spring2 BOOK
 */
@Component
@RequiredArgsConstructor
public class initDB {
    private final InitService initService;

    // 애플리케이션 로딩시점에 호출
    @PostConstruct
    public void init(){
        initService.dbInit1();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("Shin", "서울", "중구", "111");
            em.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 1);


            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }




        public void dbInit2(){
            Member member = createMember("KIM", "부산", "해운대구", "222");
            em.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book2);


            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);


            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }



        private static Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private static Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

    }


}



