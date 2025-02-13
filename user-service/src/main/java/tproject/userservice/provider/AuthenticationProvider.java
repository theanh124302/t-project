package tproject.userservice.provider;

public interface AuthenticationProvider {
    Boolean canView(String token, Long userId);
}
