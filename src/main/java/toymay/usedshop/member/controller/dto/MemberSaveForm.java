package toymay.usedshop.member.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberSaveForm {

    @NotBlank(message = "사용자 이름을 입력해주세요")
    private String nickname;

    @NotBlank(message = "성명을 입력해주세요")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "휴대폰 번호를 입력해주세요")
    private String phoneNumber;

    @NotBlank(message = "이메일을 입력해주세요")
    private String Email;

}
