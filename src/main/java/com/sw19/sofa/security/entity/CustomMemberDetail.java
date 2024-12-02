package com.sw19.sofa.security.entity;

import com.sw19.sofa.domain.member.entity.Member;
import com.sw19.sofa.domain.member.entity.enums.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomMemberDetail(Member member, Authority authority) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.<GrantedAuthority>of(new SimpleGrantedAuthority(authority.toString()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getEmail();
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
//현재 구현은 모든 계정 상태 체크 메서드가 false를 반환하고 있는데, 이는 실제 인증 과정에서 문제가 될 수 있습니다. 이러한 메서드들이 false를 반환하면 Spring Security는 해당 계정으로 인증을 허용하지 않습니다.