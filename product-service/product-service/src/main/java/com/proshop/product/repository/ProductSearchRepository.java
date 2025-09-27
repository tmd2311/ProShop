package com.proshop.product.repository;

import com.proshop.product.dto.request.ProductSearchRequest;
import com.proshop.product.entity.ProductEntity;
import java.util.List;

public interface ProductSearchRepository {
  List<ProductEntity> searchProducts(ProductSearchRequest request);
}