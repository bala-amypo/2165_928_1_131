package com.example.demo.security;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    public CustomUserDetailsService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        AppUser user = userRepository.findByEmail(email)
                .filter(AppUser::getActive)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return new User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> {
                            String name = role.getName();
                            if (!name.startsWith("ROLE_")) {
                                name = "ROLE_" + name;
                            }
                            return new SimpleGrantedAuthority(name);
                        })
                        .collect(Collectors.toSet())
        );
    }
}
