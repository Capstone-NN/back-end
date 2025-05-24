package com.capstone.NANOOM.domain.user.dto;

public record ProfileDto(String loginId,
                         String nickname,
                         String email,
                         String profileImage,
                         int nanoomScore) {
}
