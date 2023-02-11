package jpabook.jpashop.domain.repository.order.simplequery;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.domain.entity.status.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    // Dto에서는 엔티티를 파라미터로 사용하여도 문제없다.
    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus,
                               Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}

