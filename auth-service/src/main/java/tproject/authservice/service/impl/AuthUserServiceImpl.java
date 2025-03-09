package tproject.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tproject.authservice.dto.AuthUserDTO;
import tproject.authservice.repository.UserRepository;
import tproject.authservice.service.AuthUserService;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {
    private final UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username).map(AuthUserDTO::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
