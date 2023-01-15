package jpabook.jpashop.domain.entity;

import jpabook.jpashop.domain.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter

public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery") // 하나의 주문(order)에 하나의 배송(delivery)
    private Order order;

    @Embedded
    private Address address;

    /*
    EnumType.ORDINAL이 default값인데, 중간에 [READY,XXX,COMP] 다른 값이 들어오게되면 COMP값(index값)이 밀리게되어 사용하면안된다.
    .STRING은 enum의 name 즉 값이 그대로 들어간다.
    */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //[READY, COMP]
}
