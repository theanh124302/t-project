package tproject.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tproject.userservice.dto.response.privacy.UserPrivacyResponseDto;
import tproject.userservice.entity.UserPrivacyEntity;
import tproject.userservice.enumeration.UserPrivacy;
import tproject.userservice.enumeration.UserResponseMessage;
import tproject.userservice.exception.UserPrivacyNotFoundException;
import tproject.userservice.repository.UserPrivacyRepository;
import tproject.userservice.service.UserPrivacyService;

@Service
@AllArgsConstructor
public class UserPrivacyServiceImpl implements UserPrivacyService {
    private final UserPrivacyRepository userPrivacyRepository;

    @Override
    public UserPrivacyResponseDto getUserPrivacy(Long userId) {
        UserPrivacyEntity userPrivacyEntity = userPrivacyRepository.findByUserId(userId)
                .orElseThrow(() -> new UserPrivacyNotFoundException(UserResponseMessage.USER_PRIVACY_NOT_FOUND));

        return UserPrivacyResponseDto.builder()
                .userId(userPrivacyEntity.getUserId())
                .privacy(userPrivacyEntity.getPrivacy())
                .build();
    }

    @Override
    @Transactional
    public UserPrivacyResponseDto updateUserPrivacy(Long userId, UserPrivacy privacy) {
        UserPrivacyEntity userPrivacyEntity = userPrivacyRepository.findByUserId(userId)
                .orElseThrow(() -> new UserPrivacyNotFoundException(UserResponseMessage.USER_PRIVACY_NOT_FOUND));

        userPrivacyEntity.setPrivacy(privacy);
        userPrivacyRepository.save(userPrivacyEntity);

        return UserPrivacyResponseDto.builder()
                .userId(userPrivacyEntity.getUserId())
                .privacy(userPrivacyEntity.getPrivacy())
                .build();
    }

    @Override
    @Transactional
    public void initUserPrivacy(Long userId) {
        UserPrivacyEntity userPrivacyEntity = UserPrivacyEntity.builder()
                .userId(userId)
                .privacy(UserPrivacy.PUBLIC)
                .build();
        userPrivacyRepository.save(userPrivacyEntity);
    }

}
