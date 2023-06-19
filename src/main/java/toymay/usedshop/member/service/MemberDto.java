package toymay.usedshop.member.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.entity.Privacy;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;

    public static MemberDto toDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setName(member.getName());
        dto.setNickname(member.getNickname());
        dto.setPassword(member.getPrivacy().getPassword());
        dto.setEmail(member.getPrivacy().getEmail());
        dto.setPhoneNumber(member.getPrivacy().getPhoneNumber());
        return dto;
    }
}
