package toymay.usedshop.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import toymay.usedshop.common.exception.form.signUp.DuplicateNicknameException;
import toymay.usedshop.common.file.FileDto;
import toymay.usedshop.common.file.FileManager;
import toymay.usedshop.member.controller.dto.MailDto;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.entity.MemberImage;
import toymay.usedshop.member.entity.Privacy;
import toymay.usedshop.member.repository.MemberRepository;
import toymay.usedshop.security.UserDetail;

import java.io.IOException;
import java.util.Optional;

import static toymay.usedshop.common.exception.form.FormExceptionType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileManager fileManager;

    @Transactional
    //중복검사 에러 -> 표현
    public Long join(MemberDto memberDto) {
        //중복검사
        validateDuplicateMember(memberDto.getNickname(),memberDto.getPhoneNumber(), memberDto.getEmail());

        Member member = new Member(Privacy.create(bCryptPasswordEncoder.encode(memberDto.getPassword()),
                memberDto.getPhoneNumber(),memberDto.getEmail()),
                memberDto.getName(), memberDto.getNickname(), "ROLE_USER");

        memberRepository.save(member);
        return member.getId();
    }
    
    public void validateDuplicateMember(String nickname, String phoneNumber, String email) {
        Optional<Member> findNickname = memberRepository.findByNickname(nickname);
        Optional<Member> findPhoneNumber = memberRepository.findByPrivacyPhoneNumber(phoneNumber);
        Optional<Member> findEmail = memberRepository.findByPrivacyEmail(email);

        if (findNickname.isPresent()){
            throw DUPLICATE_NICKNAME.getException();
        } else if(findEmail.isPresent()){
            throw DUPLICATE_EMAIL.getException();
        } else if (findPhoneNumber.isPresent()) {
            throw DUPLICATE_PHONE_NUMBER.getException();
        }

    }

    public Long login(String nickname, String password) {
        Member member = findByNicknameOrThrow(nickname);
        if (member.getPrivacy().getPassword().equals(password)) {
            return member.getId();
        }
        else {
            throw new IllegalStateException("비밀번호가 다릅니다");
        }
    }

    private Member findByNicknameOrThrow(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow();
    }

    @Transactional
    public MailDto createMailAndChangePassword(String email) {
        String newPassword = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(email);
        dto.setTitle("임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 "
                + newPassword + " 입니다." + "로그인 후에 비밀번호를 변경을 해주세요");
        updatePasswordByEmail(newPassword,email);
        return dto;
    }

    //dto를 사용하지않아서 private로 하는것이 낫지않을까
    private void updatePasswordByEmail(String newPassword, String email) {
        Member member = memberRepository.findByPrivacyEmail(email).orElseThrow();
        //비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        member.getPrivacy().setPassword(encodedPassword);
        memberRepository.save(member);
    }

    //임시 랜덤 비밀번호
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String newPassword = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            newPassword += charSet[idx];
        }
        return newPassword;
    }

    public void mailSend(MailDto dto) {
        System.out.println("전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getAddress());
        message.setSubject(dto.getTitle());
        message.setText(dto.getMessage());
        message.setFrom("ghzm888@gmail.com");
        message.setReplyTo("ghzm888@gmail.com");
        System.out.println("message"+message);
        mailSender.send(message);
    }

    public String findEmailByNickname(String nickname) {
        Member member = findByNicknameOrThrow(nickname);
        return member.getPrivacy().getEmail();
    }

    public String findNicknameByEmail(String email) {
        Member member = memberRepository.findByPrivacyEmail(email).orElseThrow();
        //exception 처리 -> 없는 이메일일 경우
        return member.getNickname();
    }

    @Transactional
    public MemberDto findMemberDtoById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        MemberDto memberDto = MemberDto.toDto(member);
        return memberDto;
    }

    @Transactional
    public Long findIdByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetail userDetails = (UserDetail) authentication.getPrincipal();
        Long memberId = userDetails.getId();

        return memberId;
    }

    @Transactional
    public void userDetailUpdate(Long memberId, MemberDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.updatePrivacy(dto.getEmail(),dto.getPhoneNumber());
        memberRepository.save(member);
    }

    @Transactional
    public void editPassword(Long memberId, PasswordDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        member.getPrivacy().setPassword(encodedPassword);
        memberRepository.save(member);
    }

    @Transactional
    public FileDto findImageById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        MemberImage memberImage = member.getMemberImage();
        FileDto dto = FileDto.toDto(memberImage);
        return dto;
    }

    @Transactional
    public void changeMemberImage(MultipartFile imageFile, Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId).orElseThrow();
        FileDto dto = fileManager.storeImageFile(imageFile);
        MemberImage memberImage = new MemberImage(dto.getUploadFileName(), dto.getStoreFileName(), dto.getExtension());
        member.changeProfileImage(memberImage);
        memberRepository.save(member);
    }

//    @Transactional
//    public void findMemberIdByPostId(Long postId) {
//    }
}
