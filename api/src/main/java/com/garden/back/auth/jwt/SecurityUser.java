package com.garden.back.auth.jwt;

import com.garden.back.global.loginuser.MemberInfo;
import com.garden.back.member.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser implements UserDetails, MemberInfo {

    private Long memberId;
    private Role role;

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "SecurityUser{" +
            "memberId=" + memberId +
            ", role=" + role +
            '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getKey());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return String.valueOf(memberId);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Long getMemberId() {
        return memberId;
    }

    private SecurityUser(Long memberId, Role role) {
        this.memberId = memberId;
        this.role = role;
    }

    public static SecurityUser of(Long memberId, String role) {
        return new SecurityUser(memberId, Role.valueOf(role));
    }
}
