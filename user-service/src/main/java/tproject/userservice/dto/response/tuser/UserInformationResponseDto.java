package tproject.userservice.dto.response.tuser;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class UserInformationResponseDto {

    private String username;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;
}
