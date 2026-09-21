package com.shah.mini_quiz_system.service;

import com.shah.mini_quiz_system.domain.Role;
import com.shah.mini_quiz_system.domain.User;
import com.shah.mini_quiz_system.dto.request.LoginRequest;
import com.shah.mini_quiz_system.dto.request.RegisterRequest;
import com.shah.mini_quiz_system.dto.response.LoginResponse;
import com.shah.mini_quiz_system.dto.response.RegisterResponse;
import com.shah.mini_quiz_system.exception.BusinessException;
import com.shah.mini_quiz_system.mapper.LoginMapper;
import com.shah.mini_quiz_system.mapper.RegisterMapper;
import com.shah.mini_quiz_system.repoditory.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterMapper registerMapper;
    private final LoginMapper loginMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RegisterMapper registerMapper, LoginMapper loginMapper, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.registerMapper = registerMapper;
        this.loginMapper = loginMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {


        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BusinessException("Username already exists");
        }

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getUsername(),
                encodedPassword,
                Role.STUDENT
        );

        User savedUser = userRepository.save(user);

        return registerMapper.toResponse(savedUser);

    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        String token = jwtService.generateToken(
                user.getUsername()
        );

        return loginMapper.toResponse(user, token);
    }
}