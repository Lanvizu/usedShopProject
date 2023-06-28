package toymay.usedshop.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toymay.usedshop.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberImage {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String uploadFileName;
    private String storeFileName;

    @Column(length = 20)
    private String extension;

    public MemberImage(String uploadFileName, String storeFileName, String extension) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
    }

    public static MemberImage createBasicImage() {
        return new MemberImage("default_Image", "default_Image", "png");
    }
    protected void setMember(Member member) {
        this.member = member;
    }

    public String getOriginalStoreFileName() {
        return storeFileName + "." + extension;
    }
}