package com.capstone.NANOOM.domain.user;

import com.capstone.NANOOM.domain.user.dto.ProfileDto;
import com.capstone.NANOOM.domain.user.dto.UpdatePasswordRequest;
import com.capstone.NANOOM.domain.user.dto.UpdateProfileRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ProfileDto getMyProfile(String loginId);
    void updateProfile(String loginId, UpdateProfileRequest req);
    void changePassword(String loginId, UpdatePasswordRequest req);
    void withdraw(String loginId);
}
