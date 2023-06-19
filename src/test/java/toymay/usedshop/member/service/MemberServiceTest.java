package toymay.usedshop.member.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.entity.Privacy;
import toymay.usedshop.member.repository.MemberRepository;
import toymay.usedshop.security.UserDetail;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void 회원_중복(){
        //given
        MemberDto memberDto1 = new MemberDto("name","nickname",
                "1234", "qwer@gmail.com", "01012345678");
        MemberDto memberDto2 = new MemberDto("name","nickname",
                "1234", "qwer@gmail.com", "01012345678");

        memberService.join(memberDto1); // 첫번째 회원 가입
        try {
            memberService.join(memberDto2); // 두번째 회원 가입(중복)
            fail(); // 이 코드가 실행되면 테스트 실패
        } catch (IllegalStateException e) {
            // then
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다"); // 예외 메시지 검증
        }
    }

    @Test
    public void 회원가입_성공() {
        Member member = new Member(Privacy.create("1234", "01012345678", "qwer@gmail.com"),
                "name" , "nickname","ROLE_USER");
        MemberDto memberDto = new MemberDto("name", "nickname", "1234",
                "qwer@gmail.com", "01012345678");
        Long memberId = memberService.join(memberDto);
        Member member1 = memberRepository.findById(memberId).get();

        Assertions.assertThat(member.getName()).isEqualTo(member1.getName());
    }

    @Test
    public void 로그인_성공() {
        MemberDto memberDto = new MemberDto("name", "nickname", "1234",
                "qwer@gmail.com", "01012345678");
        Long memberId = memberService.join(memberDto);
//        SignInForm form = new SignInForm();
//        form.setNickname("nickname");
//        form.setPassword("1234");

//        Assertions.assertThatCode(() -> {
//            memberService.login(form);
//        }).doesNotThrowAnyException();
        assertThat(memberService.login("nickname","1234")).isEqualTo(memberId);
    }

//    @Test
//    public void 비밀번호_변경() {
//        MemberDto memberDto = new MemberDto("name", "nickname", "1234",
//                "qwer@gmail.com", "01012345678");
//        Long memberId = memberService.join(memberDto);
//        memberService.updatePasswordByEmail("123456", "qwer@gmail.com");
//
//        Member member = memberRepository.findById(memberId).orElseThrow();
//        System.out.println("member.getPrivacy().getPassword() = " + member.getPrivacy().getPassword());
//        assertThat(member.getPrivacy().getPassword()).isEqualTo("123456");
//    }

    @Test
    public void 정보_수정() {
        MemberDto memberDto = new MemberDto("name", "nickname", "1234",
                "qwer@gmail.com", "01012345678");
        Long memberId = memberService.join(memberDto);

        MemberDto changeDto = new MemberDto("name", "nickname", "1234",
                "abcd@gmail.com", "01099999999");
        memberService.userDetailUpdate(memberId, changeDto);

        Member member = memberRepository.findById(memberId).orElseThrow();

        assertThat(member.getPrivacy().getPhoneNumber()).isEqualTo("01099999999");
        assertThat(member.getPrivacy().getEmail()).isEqualTo("abcd@gmail.com");

    }

//    @Test
//    public void 비밀번호_수정() {
//        MemberDto memberDto = new MemberDto("name", "nickname", "1234",
//                "qwer@gmail.com", "01012345678");
//        Long memberId = memberService.join(memberDto);
//        PasswordDto dto = new PasswordDto();
//        dto.setPassword("1111");
//
//        memberService.editPassword(memberId, dto);
//        Member member = memberRepository.findById(memberId).orElseThrow();
//
//    }

}