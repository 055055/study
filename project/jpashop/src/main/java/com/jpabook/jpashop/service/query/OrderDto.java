package com.jpabook.jpashop.service.query;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
        List<OrderItemDto> list = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemDto orderItemDto = new OrderItemDto(orderItem);
            list.add(orderItemDto);
        }
        orderItems = list;
    }
}
