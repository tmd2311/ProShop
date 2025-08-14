package com.proshop.auth.repository;

import com.proshop.auth.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  boolean existsByAccount(String account);

  Optional<UserEntity> findByAccount(String account);

  Optional<UserEntity> findByCode(String userCode);

  Optional<UserEntity> findByEmail(String email);
}
