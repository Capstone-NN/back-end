package com.capstone.NANOOM.domain.user;

import com.capstone.NANOOM.domain.user.dto.ProfileDto;
import com.capstone.NANOOM.domain.user.dto.UpdatePasswordRequest;
import com.capstone.NANOOM.domain.user.dto.UpdateProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 현재 로그인한 사용자의 프로필 정보를 조회합니다.
     */
    @Override
    @Transactional(readOnly = true)
    public ProfileDto getMyProfile(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + loginId));

        return new ProfileDto(
                user.getLoginId(),
                user.getNickname(),
                user.getEmail(),
                user.getProfileImage(),
                user.getNanoomScore()
        );
    }

    /**
     * 프로필(닉네임, 이메일, 프로필 이미지)을 수정하고 저장합니다.
     * 프로필 이미지가 null 이면 기존 값을 그대로 유지합니다.
     */
    @Override
    public void updateProfile(String loginId, UpdateProfileRequest req) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + loginId));

        User updated = User.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .nickname(req.nickname())
                .email(req.email())
                .profileImage(req.profileImage() != null
                        ? req.profileImage()
                        : user.getProfileImage())
                .nanoomScore(user.getNanoomScore())
                .roles(user.getRoles())
                .build();

        userRepository.save(updated);
    }

    /**
     * 기존 비밀번호 검증 후, 새 비밀번호로 변경합니다.
     */
    @Override
    public void changePassword(String loginId, UpdatePasswordRequest req) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + loginId));

        if (!passwordEncoder.matches(req.oldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        User updated = User.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .password(passwordEncoder.encode(req.newPassword()))
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .nanoomScore(user.getNanoomScore())
                .roles(user.getRoles())
                .build();

        userRepository.save(updated);
    }

    /**
     * 소프트 삭제(@SQLDelete)를 트리거하여 회원을 탈퇴 처리합니다.
     */
    @Override
    public void withdraw(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + loginId));

        userRepository.delete(user);
    }
}