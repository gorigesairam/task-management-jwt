package com.taskManagment.taskproject.security;

import com.taskManagment.taskproject.entity.Users;
import com.taskManagment.taskproject.exception.UserNotFound;
import com.taskManagment.taskproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(username).orElseThrow(
                () -> new UserNotFound(String.format("User with email: %s is not found", username))
        );

        // Assign roles (for now hardcoded, later you can fetch from DB)
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");

        return new User(user.getEmail(), user.getPassword(), userAuthorities(roles));
    }

    private Collection<? extends GrantedAuthority> userAuthorities(Set<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

