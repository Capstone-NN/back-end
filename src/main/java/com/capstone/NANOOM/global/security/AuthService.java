package com.capstone.NANOOM.global.security;

import com.capstone.NANOOM.domain.user.dto.SignUpRequest;
import com.capstone.NANOOM.domain.user.dto.JwtResponse;
import com.capstone.NANOOM.domain.user.dto.LoginRequest;

public interface AuthService {
    /**
     * 회원가입: 비밀번호 암호화 후 User 저장
     */
    void register(SignUpRequest signupRequest);

    /**
     * 로그인: AuthenticationManager로 인증 위임 후 JWT 생성 · 반환
     */
    JwtResponse authenticate(LoginRequest loginRequest);
}