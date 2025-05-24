package com.capstone.NANOOM.domain.user;

import com.capstone.NANOOM.domain.user.dto.*;
import com.capstone.NANOOM.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ProfileDto getProfile(@AuthenticationPrincipal CustomUserDetails principal) {
        return userService.getMyProfile(principal.getUsername());
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateProfile(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody @Valid UpdateProfileRequest req
    ) {
        userService.updateProfile(principal.getUsername(), req);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody @Valid UpdatePasswordRequest req
    ) {
        userService.changePassword(principal.getUsername(), req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> withdraw(
            @AuthenticationPrincipal CustomUserDetails principal
    ) {
        userService.withdraw(principal.getUsername());
        return ResponseEntity.noContent().build();
    }
}