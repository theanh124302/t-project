package tproject.tauthservice.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NonNull;
import tproject.tauthservice.enumerates.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class SignupRequest {

    private String username;

    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private List<Role> roles;

}
