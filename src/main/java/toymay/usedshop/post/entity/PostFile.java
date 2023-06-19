package toymay.usedshop.post.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.product.entity.Product;

import javax.persistence.*;

@Entity
@Getter
@Table(name ="post_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String uploadFileName;
    private String storeFileName;
    private String extension;

    protected void setPost(Post post) {
        this.post = post;
    }

    public PostFile(String uploadFileName, String storeFileName, String extension) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
    }
    public String getOriginalStoreFileName() {
        return storeFileName + "." + extension;
    }
}
