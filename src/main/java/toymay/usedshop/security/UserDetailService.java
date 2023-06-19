package toymay.usedshop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

//    @Override
//    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
//        //exception 처리해주기
//        Member member = memberRepository.findByNickname(nickname).orElseThrow(()
//                -> new UsernameNotFoundException("not found Nickname : " + nickname));
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getAuth()));
//        return new UserDetail(member);
//    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new UsernameNotFoundException("Nickname not found: " + nickname));

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(member.getAuth());

        return new UserDetail(member, authorities);
    }
}
