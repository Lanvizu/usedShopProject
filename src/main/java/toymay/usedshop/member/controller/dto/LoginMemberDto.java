package toymay.usedshop.member.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginMemberDto {
//    nickname memberId imagecode
    private Long memberId;
    private String nickname;
    private String memberImageName;
}
