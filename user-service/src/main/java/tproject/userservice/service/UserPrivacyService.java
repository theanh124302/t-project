package tproject.userservice.service;

import tproject.userservice.dto.response.privacy.UserPrivacyResponseDto;
import tproject.userservice.enumeration.UserPrivacy;

public interface UserPrivacyService {
    UserPrivacyResponseDto getUserPrivacy(Long userId);
    UserPrivacyResponseDto updateUserPrivacy(Long userId, UserPrivacy privacy);
    void initUserPrivacy(Long userId);
}
