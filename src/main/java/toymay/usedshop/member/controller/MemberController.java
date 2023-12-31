package toymay.usedshop.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toymay.usedshop.common.exception.form.CustomFormException;
import toymay.usedshop.member.controller.dto.LoginMemberDto;
import toymay.usedshop.member.controller.dto.MailDto;
import toymay.usedshop.member.controller.dto.MemberSaveForm;
import toymay.usedshop.member.service.MemberDto;
import toymay.usedshop.member.service.MemberService;
import toymay.usedshop.member.service.PasswordDto;
import toymay.usedshop.post.service.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

    @GetMapping("/")
    public String main(
//            @RequestParam(value = "errorMessage", required = false) String errorMessage,
            Model model, HttpServletRequest request) {
        String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        model.addAttribute("errorMessage", errorMessage);

        return ("member/main");
    }


    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("memberSaveForm", new MemberSaveForm());
        return ("member/signUp");
    }

    @PostMapping("/signUp")
    public String signUp(@Valid MemberSaveForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "member/signUp";
        }
        try {
            memberService.join(new MemberDto(
                    form.getName(),
                    form.getNickname(),
                    form.getPassword(),
                    form.getEmail(),
                    form.getPhoneNumber()));
            return "redirect:/";
        } catch (CustomFormException e) {
            result.rejectValue(e.getField(), e.getErrorCode(), e.getMessage());
        }
        return "member/signUp";
    }

    @GetMapping("/findNickname")
    public String findNicknamePage() {
        return ("member/findNickname");
    }

    @PostMapping("/findNickname")
    public String findNickname(@RequestParam("email") String email, Model model) {
        try {
            String nickname = memberService.findNicknameByEmail(email);
            model.addAttribute("nickname", nickname);
        }catch (CustomFormException e) {
            model.addAttribute("message", e.getMessage());
        }
        return ("member/findNickname");
    }

    @GetMapping("/findPassword")
    public String findPassword() {
        return ("member/findPassword");
    }

    @PostMapping("/findPassword")
    public String sendEmail(@RequestParam("nickname") String nickname, Model model) {
        try {
            String email = memberService.findEmailByNickname(nickname);
            MailDto dto = memberService.createMailAndChangePassword(email);
            memberService.mailSend(dto);
            return "redirect:/";
        } catch (CustomFormException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "member/findPassword";
    }


    @GetMapping("member/{memberId}")
    @PreAuthorize("#memberId == authentication.principal.id")
    public String memberInfoPage(@PathVariable ("memberId") Long memberId, Model model) {

        LoginMemberDto loginMemberDto = postService.loginMemberDtoByMemberId(memberId);
        model.addAttribute("member2", loginMemberDto);

        MemberDto memberDto = memberService.findMemberDtoById(memberId);
        model.addAttribute("member", memberDto);

        return ("member/memberDetail");
    }

    @GetMapping("member/{memberId}/edit")
    @PreAuthorize("#memberId == authentication.principal.id")
    public String memberEditPage(@PathVariable("memberId") Long memberId, Model model) {

        LoginMemberDto loginMemberDto = postService.loginMemberDtoByMemberId(memberId);
        model.addAttribute("member2", loginMemberDto);

        MemberDto memberDto = memberService.findMemberDtoById(memberId);
        model.addAttribute("member", memberDto);
        return ("member/editForm");
    }

    @PostMapping("member/{memberId}/edit")
    public String memberEdit(@PathVariable("memberId") Long memberId,
                             @ModelAttribute MemberDto dto, RedirectAttributes redirectAttributes) {
        memberService.userDetailUpdate(memberId, dto);
        redirectAttributes.addAttribute("status", true);

        return ("redirect:/member/{memberId}");
    }

    @GetMapping("/member/{memberId}/editPassword")
    public String editPasswordPage(@PathVariable("memberId") Long memberId, Model model) {
        LoginMemberDto loginMemberDto = postService.loginMemberDtoByMemberId(memberId);
        model.addAttribute("member", loginMemberDto);
        return ("member/editPasswordForm");
    }

    //비밀번호 확인칸 만들기.
    @PostMapping("/member/{memberId}/editPassword")
    public String editPassword(@PathVariable("memberId") Long memberId,
                               @ModelAttribute PasswordDto dto, RedirectAttributes redirectAttributes) {
        memberService.editPassword(memberId, dto);
        redirectAttributes.addAttribute("status", true);

        return ("redirect:/member/{memberId}");
    }

    @GetMapping("/member/{memberId}/editImage")
    public String editImagePage(@PathVariable("memberId") Long memberId, Model model) {
        LoginMemberDto loginMemberDto = postService.loginMemberDtoByMemberId(memberId);
        model.addAttribute("member", loginMemberDto);
        return ("member/editImageForm");
    }

    @PostMapping("/member/{memberId}/editImage")
    public String editImage(@PathVariable("memberId") Long memberId, @RequestParam ("imageFile") MultipartFile imageFile ,
                            RedirectAttributes redirectAttributes)throws IOException {
        memberService.changeMemberImage(imageFile, memberId);
        redirectAttributes.addAttribute("status", true);

        return ("redirect:/member/{memberId}");
    }


    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
