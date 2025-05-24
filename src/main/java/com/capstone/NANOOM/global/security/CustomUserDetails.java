package com.capstone.NANOOM.global.security;

import com.capstone.NANOOM.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Spring Security가 사용하는 사용자 정보 래퍼
 */
public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 로직이 있으면 해당 로직으로 대체
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getDeletedAt() == null; // 소프트 삭제된 계정은 잠김 처리
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 로직이 있으면 해당 로직으로 대체
    }

    @Override
    public boolean isEnabled() {
        return user.getDeletedAt() == null; // 삭제되지 않은 계정만 활성
    }

    /**
     * User 엔티티를 반환하고 싶을 때 사용
     */
    public User getUser() {
        return user;
    }
}
