package com.sreekutt.ProjectManagementSystem.service;

import com.sreekutt.ProjectManagementSystem.entity.UserEntity;
import com.sreekutt.ProjectManagementSystem.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Extracted email from UUUUU: " + email);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException(("USer not found")));
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())  // e.g., ROLE_ADMIN
        );

        System.out.println("User role from DB: " + user.getRole());
        System.out.println("Authorities: " + authorities);


        return new User(user.getEmail(),user.getPassword(),authorities);
    }
}
