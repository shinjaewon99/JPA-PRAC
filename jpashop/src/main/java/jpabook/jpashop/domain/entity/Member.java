package jpabook.jpashop.domain.entity;

import jpabook.jpashop.domain.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    // @GeneratedValue = 자동생성
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    /*
      @Embeddable을 통해 클래스를 만들어줌으로써, @Embedded로 표시하여 내장되어있다고 보여줌(생략해도되지만 표시하는걸 권장)
    */
    @Embedded
    private Address address;


    /* 하나의 회원이 주문(orders)를 많이 할수있음으로 One(회원)To Many(주문)
       order테이블에 있는 member필드에 의해서 mapping이 된것이다. (연관관계 주인은 Order테이블)
    */
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
