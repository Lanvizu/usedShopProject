package toymay.usedshop.order.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toymay.usedshop.order.entity.Order;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long productId;
    private Long memberId;
    private int bidPrice;

    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto(order.getProduct().getId(), order.getMember().getId(), order.getBidPrice());
        return orderDto;
    }

    public static List<OrderDto> orderDtos(List<Order> orders) {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderDto orderDto = new OrderDto(order.getProduct().getId(), order.getMember().getId(), order.getBidPrice());
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }
}
