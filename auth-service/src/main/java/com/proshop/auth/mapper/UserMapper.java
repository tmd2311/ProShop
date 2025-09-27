package com.proshop.auth.mapper;

import com.proshop.auth.dto.response.AuthInfoResponse;
import com.proshop.auth.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  AuthInfoResponse toDTO(UserEntity userEntity);

  UserEntity toEntity(AuthInfoResponse userInfoResponse);

}
