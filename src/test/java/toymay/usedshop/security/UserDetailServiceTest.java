package toymay.usedshop.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.extras.springsecurity5.auth.Authorization;
import toymay.usedshop.member.repository.MemberRepository;
import toymay.usedshop.member.service.MemberDto;
import toymay.usedshop.member.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class UserDetailServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UserDetailService userDetailService;


}