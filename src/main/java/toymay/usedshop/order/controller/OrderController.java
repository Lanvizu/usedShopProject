package toymay.usedshop.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import toymay.usedshop.order.controller.dto.BidPriceForm;
import toymay.usedshop.order.controller.dto.OrderIdDto;
import toymay.usedshop.order.service.OrderDto;
import toymay.usedshop.order.service.OrderService;
import toymay.usedshop.post.controller.dto.PostSaveForm;

@Controller
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/post/{orderId}/approveBid")
    public String approveBid(@PathVariable("orderId") Long orderId) {
        OrderIdDto orderIdDto = orderService.findOrderDtoById(orderId);
        orderService.approveBidPrice(orderIdDto);
        return "redirect:/mainPage";
    }

    @PostMapping("/post/order/{orderId}")
    public String updateOrder(@PathVariable Long orderId, @RequestParam String action,
                              @RequestParam(value = "updateBidPrice", required = false) Integer updateBidPrice){
            if(action.equals("update")){
                orderService.updateOrder(orderId, updateBidPrice);
            } else if (action.equals("cancel")) {
                orderService.cancelOrder(orderId);
            }
            return ("redirect:/mainPage");
        }

//    @PostMapping("/post/approveBid")
//    public String approveBid(@RequestParam("order") String orderJson) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            OrderDto order = objectMapper.readValue(orderJson, OrderDto.class);
//            System.out.println("order = " + order);
//            orderService.approveBidPrice(order);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return "redirect:/mainPage";
//    }
}
