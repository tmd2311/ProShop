package com.proshop.product.repository;

import com.proshop.product.dto.request.ProductSearchRequest;
import com.proshop.product.entity.BrandEntity;
import com.proshop.product.entity.CategoryEntity;
import com.proshop.product.entity.ProductEntity;
import com.proshop.product.entity.SKUEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class ProductSearchRepositoryImpl implements ProductSearchRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<ProductEntity> searchProducts(ProductSearchRequest request) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<ProductEntity> query = cb.createQuery(ProductEntity.class);
    Root<ProductEntity> product = query.from(ProductEntity.class);

    Join<ProductEntity, CategoryEntity> categoryJoin = product.join("category", JoinType.LEFT);
    Join<ProductEntity, BrandEntity> brandJoin = product.join("brand", JoinType.LEFT);
    Join<ProductEntity, SKUEntity> skuJoin = product.join("skus", JoinType.LEFT);

    List<Predicate> predicates = new ArrayList<>();
    if (StringUtils.hasText(request.getKeyword())) {
      predicates.add(cb.like(cb.lower(product.get("name")),
          "%" + request.getKeyword().toLowerCase() + "%"));
    }
    if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
      predicates.add(categoryJoin.get("id").in(request.getCategoryIds()));
    }
    if (request.getBrandIds() != null && !request.getBrandIds().isEmpty()) {
      predicates.add(brandJoin.get("id").in(request.getBrandIds()));
    }
    if (request.getMinPrice() != null || request.getMaxPrice() != null) {
      if (request.getMinPrice() != null) {
        predicates.add(cb.greaterThanOrEqualTo(skuJoin.get("price"), request.getMinPrice()));
      }
      if (request.getMaxPrice() != null) {
        predicates.add(cb.lessThanOrEqualTo(skuJoin.get("price"), request.getMaxPrice()));
      }
    }

    if (!predicates.isEmpty()) {
      query.where(cb.and(predicates.toArray(new Predicate[0])));
    }
    query.distinct(true);

    return entityManager.createQuery(query).getResultList();
  }
}
