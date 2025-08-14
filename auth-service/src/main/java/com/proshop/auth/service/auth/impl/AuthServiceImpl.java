package com.proshop.auth.service.auth.impl;

import com.proshop.auth.dto.request.ChangePasswordRequest;
import com.proshop.auth.dto.request.LoginRequest;
import com.proshop.auth.dto.request.LogoutRequest;
import com.proshop.auth.dto.response.LoginResponse;
import com.proshop.auth.dto.response.UserInfoResponse;
import com.proshop.auth.entity.UserEntity;
import com.proshop.auth.exceptions.ResException;
import com.proshop.auth.repository.UserRepository;
import com.proshop.auth.service.auth.AuthService;
import com.proshop.auth.utils.enums.ResErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;




  @Override
  @Transactional
  public LoginResponse login(LoginRequest request) {
    validateLoginRequest(request);
    try {
      Authentication authenticate = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
            request.getAccount(), request.getPassword()));
      UserEntity user = (UserEntity) authenticate.getPrincipal();
      return makeLoginResponse(user);
    } catch (LockedException ex) {
      throw new ResException(ResErrorCode.ACCOUNT_BLOCKED);
    } catch (DisabledException ex) {
      throw new ResException(ResErrorCode.ACCOUNT_DELETED);
    } catch (BadCredentialsException ex) {
      log.error("Login failed", ex);
      throw new ResException(ResErrorCode.INVALID_USER_PASS);
    }
  }

  @Override
  public LoginResponse makeLoginResponse(UserEntity entity) {
    return null;
  }

  @Override
  public LoginResponse makeLoginResponse(UserEntity entity, String provider) {
    return null;
  }

  @Override
  public UserInfoResponse changePassword(ChangePasswordRequest req, String userCode) {
    return null;
  }

  @Override
  public Boolean logout(LogoutRequest req) {
    return null;
  }

  private void validateLoginRequest(LoginRequest request) {
    if (request.getPassword().isEmpty()) {
      throw new ResException(ResErrorCode.INVALID_USER_PASS);
    }
    UserEntity userEntity = userRepository.findByAccount(request.getAccount()).orElseThrow(() -> new ResException(ResErrorCode.INVALID_USER_PASS));
    if (Boolean.TRUE.equals(userEntity.getDeleted())) {
      throw new ResException(ResErrorCode.ACCOUNT_DELETED);
    }
  }
}
