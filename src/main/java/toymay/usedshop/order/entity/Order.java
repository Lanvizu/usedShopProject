package toymay.usedshop.order.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toymay.usedshop.common.BaseEntity;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.entity.MemberImage;
import toymay.usedshop.member.entity.Privacy;
import toymay.usedshop.post.entity.Post;
import toymay.usedshop.product.entity.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="product_id")
    private Product product;

    private int bidPrice;

    public Order(Member member, Product product, int bidPrice) {
        this.member = member;
        this.product = product;
        this.bidPrice = bidPrice;
    }

    public void update(int bidPrice) {
        this.bidPrice = bidPrice;
    }
}
