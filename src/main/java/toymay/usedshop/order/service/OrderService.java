package toymay.usedshop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.repository.MemberRepository;
import toymay.usedshop.member.service.MemberDto;
import toymay.usedshop.member.service.MemberService;
import toymay.usedshop.order.controller.dto.OrderIdDto;
import toymay.usedshop.order.entity.Order;
import toymay.usedshop.order.repository.OrderRepository;
import toymay.usedshop.post.entity.Post;
import toymay.usedshop.post.service.PostDto;
import toymay.usedshop.product.entity.Product;
import toymay.usedshop.product.entity.ProductStatus;
import toymay.usedshop.product.repository.ProductRepository;
import toymay.usedshop.product.service.ProductService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long saveOrder(OrderDto orderDto) {
        Long memberId = orderDto.getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow();

        Long productId = orderDto.getProductId();
        Product product = productRepository.findById(productId).orElseThrow();

        Order order = new Order(member, product, orderDto.getBidPrice());
        orderRepository.save(order);
        Long orderId = order.getId();
        return orderId;
    }
    @Transactional
    public List<OrderIdDto> findOrderDtosByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<Order> orders = product.getOrders();
        List<OrderIdDto> orderIdDtos = OrderIdDto.orderIdDtos(orders);
        return orderIdDtos;
    }

    @Transactional
    public OrderIdDto findOrderDtoById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        OrderIdDto orderIdDto = OrderIdDto.toDto(order);
        return orderIdDto;
    }

    @Transactional
    public void updateOrder(Long orderId, int bidPrice) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.update(bidPrice);
        System.out.println("bidPrice = " + bidPrice);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Transactional
    public void approveBidPrice(OrderIdDto orderIdDto) {
        Product product = productRepository.findById(orderIdDto.getProductId()).orElseThrow();
        product.setSalePrice(orderIdDto.getBidPrice());
        product.setStatus(ProductStatus.SoldOut);
        product.setBuyerId(orderIdDto.getMemberId());
        productRepository.save(product);
    }
}
