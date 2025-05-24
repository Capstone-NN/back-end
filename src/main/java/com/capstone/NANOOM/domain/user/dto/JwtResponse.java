package com.capstone.NANOOM.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 성공 시 클라이언트에게 반환할 JWT 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    /** 실제 토큰 문자열 */
    private String accessToken;

    /** 토큰 타입 (일반적으로 "Bearer") */
    private String tokenType = "Bearer";

    /** 토큰 만료까지 남은 시간 (초 단위) */
    private long expiresIn;
}