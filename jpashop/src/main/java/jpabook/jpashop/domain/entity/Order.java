package jpabook.jpashop.domain.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
/*
테이블 이름을 orders로 정하지 않으면
Order라는 어노테이션이 사용된다.
*/
@Getter
@Setter

public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;


    @ManyToOne // order입장에서는 many(주문)이 one(한명의 회원)한테 오는것임으로 ManyToOne
    @JoinColumn(name = "member_id") // Member의 Pk를 Join해준다,
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태[ORDER, CANCEL]


}
