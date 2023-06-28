package toymay.usedshop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toymay.usedshop.order.repository.OrderRepository;
import toymay.usedshop.post.entity.Post;
import toymay.usedshop.post.repository.PostRepository;
import toymay.usedshop.product.entity.Product;
import toymay.usedshop.product.entity.ProductStatus;
import toymay.usedshop.product.repository.ProductRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final PostRepository postRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long saveNameAndPrice(ProductDto dto) {
        Long postId = dto.getPostId();
        Post post = postRepository.findById(postId).orElseThrow();
        Product product = new Product(post, dto.getName(), dto.getRegistrationPrice());
        //초기 판매 금액, 판매 중 설정.
        product.setSalePrice(dto.getRegistrationPrice());
        product.setStatus(ProductStatus.OnSale);
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public void changeNameAndPrice(Long productId, String name, int registrationPrice) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.changeNameAndPrice(name, registrationPrice);
    }

//    @Transactional
//    public ProductDto findProductDtoByPostId(Long postId) {
//        Post post = postRepository.findById(postId).orElseThrow();
//        int registrationPrice = post.getProduct().getRegistrationPrice();
//        String name = post.getProduct().getName();
//        ProductStatus status = post.getProduct().getStatus();
//        ProductDto productDto = new ProductDto(name, registrationPrice, postId, status);
//        return productDto;
//    }

    @Transactional
    public Long findProductIdByPostId(Long postId) {
        Long productId = postRepository.findById(postId).orElseThrow().getProduct().getId();
        return productId;
    }

    @Transactional
    public int findSalePriceByPostId(Long postId) {
        int salePrice = postRepository.findById(postId).orElseThrow().getProduct().getSalePrice();
        return salePrice;
    }
}
