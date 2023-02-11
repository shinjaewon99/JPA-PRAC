package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.entity.Order;
import jpabook.jpashop.domain.entity.status.OrderStatus;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import jpabook.jpashop.domain.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.domain.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * x To One (ManyToOne, OneToOne) 관계
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName(); // LAZY 강제 초기화, order.getMember()까지는 아직 프록시다, (DB에 데이터가 안날라간상태)
            order.getDelivery().getAddress(); // LAZY 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    // 제네릭 타입으로 한번 감싸야된다, 여기서는 아직 X
    public List<SimpleOrderDto> ordersV2(){

        // ORDER 주문이 2개
        // N + 1 문제 --> 1 + 회원 N + 배송 N = 5번
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());


        List<SimpleOrderDto> result = orders.stream()
                //.map = a를 b로 바꾼다
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }


    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {

        return orderSimpleQueryRepository.findOrderDtos();

    }

    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        // Dto에서는 엔티티를 파라미터로 사용하여도 문제없다.
        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }
}
