package toymay.usedshop.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import toymay.usedshop.member.service.MemberDto;
import toymay.usedshop.order.entity.Order;
import toymay.usedshop.post.entity.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private Privacy privacy;

    private String name;
    private String nickname;

    private String auth; //USER, ADMIN

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberImage memberImage;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    //초기화 작업
    private final List<Order> orders = new ArrayList<>();

    public Member(Privacy privacy, String name, String nickname, String auth) {
        this.privacy = privacy;
        this.name = name;
        this.nickname = nickname;
        this.auth = auth;
        changeProfileImage(MemberImage.createBasicImage());
    }

    public void changeProfileImage(MemberImage memberImage) {
        this.memberImage = memberImage;
        memberImage.setMember(this);
    }

    public void updatePrivacy(String email, String phoneNumber) {
        this.privacy.setEmail(email);
        this.privacy.setPhoneNumber(phoneNumber);

    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

//    public void updatePrivacy(Privacy privacy) {
//        this.privacy = privacy;
//    }

//    public void validateNickname(String nickname) {
//    }
//
//    private boolean isNumber(String nickname) {
//        if (nickname.isBlank()) {
//            return false;
//        }
//
//        return nickname.chars().allMatch(Character::isDigit);
//    }
//
//    private boolean isValidFormat(String nickname) {
//        return nickname.matches("^[a-zA-Z가-힣\\d._]+$");
//    }
}