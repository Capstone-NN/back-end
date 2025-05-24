package com.capstone.NANOOM.domain.user;

import com.capstone.NANOOM.domain.user.dto.JwtResponse;
import com.capstone.NANOOM.domain.user.dto.LoginRequest;
import com.capstone.NANOOM.domain.user.dto.SignUpRequest;
import com.capstone.NANOOM.global.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입 요청 처리
     */
    @PostMapping("/signup")
    public ResponseEntity<Void> register(@RequestBody @Valid SignUpRequest signupRequest) {
        authService.register(signupRequest);
        System.out.println("컨트롤러 테스트");
        log.debug("컨트롤러 로그 테스트");
        return ResponseEntity.ok().build();
    }

    /**
     * 로그인 요청 처리 및 JWT 응답 반환
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticate(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
