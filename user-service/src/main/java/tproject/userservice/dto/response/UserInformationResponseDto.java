package tproject.userservice.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationResponseDto {
    private Long id;
    private Long authId;
    private String username;
    private String fullName;
    private String address;
    private String bio;
    private String status;
    private String avatarUrl;
    private Long age;
}
