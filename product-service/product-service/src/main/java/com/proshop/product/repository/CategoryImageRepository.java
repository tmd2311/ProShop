package com.proshop.product.repository;

import com.proshop.product.entity.CategoryImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CategoryImageRepository extends JpaRepository<CategoryImageEntity, UUID> {
}
