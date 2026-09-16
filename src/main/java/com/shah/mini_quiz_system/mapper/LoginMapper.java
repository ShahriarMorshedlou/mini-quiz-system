package com.shah.mini_quiz_system.mapper;

import com.shah.mini_quiz_system.domain.User;
import com.shah.mini_quiz_system.dto.response.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    LoginResponse toResponse(User user);
}