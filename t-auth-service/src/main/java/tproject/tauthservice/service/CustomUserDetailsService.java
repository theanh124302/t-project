package tproject.tauthservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tproject.tauthservice.entity.RoleEntity;
import tproject.tauthservice.repository.AuthenticationRepository;
import tproject.tauthservice.repository.RoleRepository;
import tproject.tauthservice.repository.UserRepository;
import tproject.tauthservice.dto.AuthenticationDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final AuthenticationRepository authenticationRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return authenticationRepository.findByUsername(username)
                    .map(auth -> {
                        log.info("User with username {} found", username);
                        List<RoleEntity> roles = roleRepository.findRolesByUserId(auth.getUserId());
                        log.info("User with username {} has roles: {}", username, roles);
                        return new AuthenticationDto(auth, roles);
                    }).orElseThrow();
        } catch (Exception e) {
            log.error(Exception.class.getName(), e);
            throw new RuntimeException("");
        }

    }
}
