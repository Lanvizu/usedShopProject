package toymay.usedshop.post.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import toymay.usedshop.common.BaseEntity;
import toymay.usedshop.file.FileDto;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.entity.MemberImage;
import toymay.usedshop.product.entity.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    private String text;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PostFile> postFiles = new ArrayList<>();

    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Product product;

    public Post(Member member, String text) {
        this.member = member;
        this.text = text;
    }
    public void addPostFile(PostFile postFile) {
        postFiles.add(postFile);
        postFile.setPost(this);
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void changeText(String text) {
        this.text = text;
    }

}
