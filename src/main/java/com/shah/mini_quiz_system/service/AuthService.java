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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterMapper registerMapper;
    private final LoginMapper loginMapper;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RegisterMapper registerMapper, LoginMapper loginMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.registerMapper = registerMapper;
        this.loginMapper = loginMapper;
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

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("Username or password is incorrect"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()

        )) {
            throw new BusinessException("Username or password is incorrect");
        }

        return loginMapper.toResponse(user);


    }
}
