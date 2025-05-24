package com.capstone.NANOOM.domain.user.dto;

public record SignUpRequest(String loginId, String password, String nickname, String email, String profileImage) {
}
