package jpabook.jpashop.domain.repository.order.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.domain.entity.status.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

}
