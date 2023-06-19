package toymay.usedshop.order.controller.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toymay.usedshop.order.entity.Order;
import toymay.usedshop.order.service.OrderDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
public class OrderIdDto {
    private Long id;
    private Long productId;
    private Long memberId;
    private int bidPrice;

    public static OrderIdDto toDto(Order order) {
        OrderIdDto orderIdDto = new OrderIdDto(order.getId(),order.getProduct().getId(),
                order.getMember().getId(), order.getBidPrice());
        return orderIdDto;
    }

    public static List<OrderIdDto> orderIdDtos(List<Order> orders) {
        List<OrderIdDto> orderIdDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderIdDto orderIdDto = new OrderIdDto(order.getId(),order.getProduct().getId(), order.getMember().getId(), order.getBidPrice());
            orderIdDtos.add(orderIdDto);
        }
        return orderIdDtos;
    }
}
