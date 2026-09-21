package com.shah.mini_quiz_system.mapper;

import com.shah.mini_quiz_system.domain.User;
import com.shah.mini_quiz_system.dto.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginMapper {


    @Mapping(target = "token", source = "token")
    LoginResponse toResponse(User user, String token);
}