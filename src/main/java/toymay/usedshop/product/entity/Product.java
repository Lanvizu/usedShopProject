package toymay.usedshop.product.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toymay.usedshop.order.entity.Order;
import toymay.usedshop.post.entity.Post;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="post_id")
    private Post post;

    private String name;
    private int salePrice;
    private int registrationPrice;
    private Long buyerId;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // 주문상태 [SoldOut,OnSale]

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Order> orders = new ArrayList<>();

    public Product(Post post, String name, int registrationPrice) {
        this.post = post;
        this.name = name;
        this.registrationPrice = registrationPrice;
    }
    public void changeNameAndPrice(String name, int registrationPrice) {
        this.name = name;
        this.registrationPrice = registrationPrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setBuyerId(Long buyerId){
        this.buyerId = buyerId;
    }

}
