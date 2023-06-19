package toymay.usedshop.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import toymay.usedshop.member.entity.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class UserDetail implements UserDetails {


    private final Member member;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetail(Member member, Collection<? extends GrantedAuthority> authorities) {
        this.member = member;
        this.authorities = authorities;
    }

    public Long getId() {
        return member.getId();
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add(new SimpleGrantedAuthority(member.getAuth()));
//        return collection;
////        return getAuthorities();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
//    @Override
//    public List<GrantedAuthority> getAuthorities() {
//        return new ArrayList<>(authorities);
//    }

    @Override
    public String getPassword() {
        return member.getPrivacy().getPassword();
    }

    @Override
    public String getUsername() {
        return member.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
