package toymay.usedshop.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.entity.MemberImage;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByPrivacyPhoneNumber(String phoneNumber);
    Optional<Member> findByPrivacyEmail(String email);
}
