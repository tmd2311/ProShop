package com.proshop.product.repository;

import com.proshop.product.entity.SKUEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface SKURepository extends JpaRepository<SKUEntity, UUID> {
  boolean existsBySkuCode(String skuCode);
}

