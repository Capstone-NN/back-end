package com.capstone.NANOOM.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(@NotBlank @Size(max=20) String nickname,
                                   @NotBlank @Email String email,
                                   String profileImage) {
}
