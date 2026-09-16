package com.shah.mini_quiz_system.mapper;

import com.shah.mini_quiz_system.domain.User;
import com.shah.mini_quiz_system.dto.response.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterMapper {

    RegisterResponse toResponse(User user);
}
