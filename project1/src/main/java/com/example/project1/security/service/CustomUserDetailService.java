package com.example.project1.security.service;

import com.example.project1.domain.User;
import com.example.project1.exception.CommonException;
import com.example.project1.exception.ErrorCode;
import com.example.project1.repository.UserRepository;
import com.example.project1.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info(username);
        UserRepository.UserSecurityForm user = userRepository.findUserIdAndRoleBySerialId(Long.valueOf(username))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return CustomUserDetails.create(user);
    }

    public UserDetails loadUserById(Long userId) throws CommonException {
        UserRepository.UserSecurityForm user = userRepository.findByIdAndIsLoginAndRefreshTokenIsNotNull(userId, true)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found UserId"));

        return CustomUserDetails.create(user);
    }
}