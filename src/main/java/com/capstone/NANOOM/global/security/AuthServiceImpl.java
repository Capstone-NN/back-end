package com.capstone.NANOOM.global.security;

import com.capstone.NANOOM.domain.user.Role;
import com.capstone.NANOOM.domain.user.User;
import com.capstone.NANOOM.domain.user.UserRepository;
import com.capstone.NANOOM.domain.user.dto.JwtResponse;
import com.capstone.NANOOM.domain.user.dto.LoginRequest;
import com.capstone.NANOOM.domain.user.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void register(SignUpRequest signUpRequest) {
        // 중복 회원 검사
        if (userRepository.existsByLoginId(signUpRequest.loginId())) {
            throw new IllegalArgumentException("이미 사용중인 로그인 ID 입니다.");
        }
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new IllegalArgumentException("이미 사용중인 이메일 입니다.");
        }

        // 엔티티 생성
        User user = User.builder()
                .loginId(signUpRequest.loginId())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .nickname(signUpRequest.nickname())
                .email(signUpRequest.email())
                .profileImage(signUpRequest.profileImage())   // DTO에 있으면, 없으면 기본값 사용
                .nanoomScore(0)
                .build();

        // 기본 ROLE 부여
        user.getRoles().add(Role.ROLE_USER);

        userRepository.save(user);
    }

    @Override
    public JwtResponse authenticate(LoginRequest loginRequest) {
        // 1) 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.loginId(),
                        loginRequest.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2) JWT 생성
        String token = jwtTokenProvider.generateToken(authentication);
        long expiresIn = jwtTokenProvider.getExpiryDuration(); // 초 단위 만료 시간

        return new JwtResponse(token, "Bearer", expiresIn);
    }
}