package com.proshop.auth.service.user.impl;

import com.proshop.auth.dto.response.GeneralResponse;
import com.proshop.auth.dto.response.PageResponse;
import com.proshop.auth.dto.response.PageResponseUtil;
import com.proshop.auth.dto.response.ResponseStatus;
import com.proshop.auth.dto.response.UserInfoResponse;
import com.proshop.auth.entity.RoleEntity;
import com.proshop.auth.entity.UserEntity;
import com.proshop.auth.exceptions.ResException;
import com.proshop.auth.mapper.UserMapper;
import com.proshop.auth.repository.UserRepository;
import com.proshop.auth.service.user.UserService;
import com.proshop.auth.utils.enums.ResErrorCode;
import com.proshop.auth.utils.enums.UserStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  @Transactional(readOnly = true)
  public GeneralResponse<PageResponse<UserInfoResponse>> getAllUsers(Pageable pageable) {
    Page<UserEntity> usersPage = userRepository.findAllByDeletedFalse(pageable);
    List<UserInfoResponse> listUserInfoResponses = new ArrayList<>();

    for (UserEntity user : usersPage) {
      UserInfoResponse userInfoResponse = toDTO(user);
      listUserInfoResponses.add(userInfoResponse);
    }

    PageResponse<UserInfoResponse> pageResponse = PageResponseUtil.buildPageResponse(
        listUserInfoResponses,
        usersPage.getTotalElements(),
        pageable.getPageNumber(),
        pageable.getPageSize()
    );

    return new GeneralResponse<>(
        ResponseStatus.SUCCESS_STATUS,
        pageResponse,
        null
    );
  }

  @Override
  public void deactivateUser(Long id) {
    log.info("Deactivating user with ID: {}", id);
    UserEntity userEntity = userRepository.findById(id)
        .orElseThrow(() -> new ResException(ResErrorCode.USER_NOT_FOUND));
    userEntity.setStatus(UserStatus.INACTIVE.name());
    userRepository.save(userEntity);
    log.info("Deactivated user successfully with ID: {}", id);
  }

  @Override
  public void activateUser(Long id) {
    log.info("Activating user with ID: {}", id);
    UserEntity userEntity = userRepository.findById(id)
        .orElseThrow(() -> new ResException(ResErrorCode.USER_NOT_FOUND));
    userEntity.setStatus(UserStatus.ACTIVE.toString());
    userRepository.save(userEntity);
    log.info("Activated user successfully with ID: {}", id);
  }

  @Override
  public void deleteUser(Long id) {
    log.info("Deleting user with ID: {}", id);
    if (!userRepository.existsById(id)) {
      throw new ResException(ResErrorCode.USER_NOT_FOUND);
    }
    userRepository.deleteById(id);
    log.info("Deleted user successfully with ID: {}", id);
  }

  @Override
  public void softDeleteUser(Long id) {
    log.info("Soft deleting user with ID: {}", id);
    UserEntity userEntity = userRepository.findById(id)
        .orElseThrow(() -> new ResException(ResErrorCode.USER_NOT_FOUND));
    userEntity.setDeleted(true);
    userEntity.setStatus(UserStatus.INACTIVE.name());
    userRepository.save(userEntity);
    log.info("Soft deleted user successfully with ID: {}", id);
  }

  // Chuyển UserEntity -> UserInfoResponse
  private UserInfoResponse toDTO(UserEntity entity) {
    if (entity == null) return null;

    UserInfoResponse dto = new UserInfoResponse();
    dto.setCode(entity.getCode());
    dto.setAccount(entity.getAccount());
    dto.setUsername(entity.getUsername());
    dto.setFullName(entity.getFullName());
    dto.setAvatarUrl(entity.getAvatarUrl());
    dto.setCurrentAddress(entity.getCurrentAddress());
    dto.setLastLogin(entity.getLastLogin());
    dto.setEmail(entity.getEmail());
    dto.setStatus(entity.getStatus());

    if (entity.getRoles() != null) {
      List<String> roleNames = entity.getRoles().stream()
          .map(RoleEntity::getName)
          .collect(Collectors.toList());
      dto.setRoles(roleNames);
    } else {
      dto.setRoles(new ArrayList<>());
    }

    return dto;
  }

}
