package tproject.tauthservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tproject.tauthservice.repository.AuthenticationRepository;
import tproject.tauthservice.repository.UserRepository;
import tproject.tauthservice.dto.AuthenticationDto;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthenticationRepository authenticationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationRepository.findAuthenticationDtoByUsername(username).map(AuthenticationDto::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}
