package com.proshop.product.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchRequest {

  private String keyword;
  private List<UUID> categoryIds;
  private List<UUID> brandIds;
  private BigDecimal minPrice;
  private BigDecimal maxPrice;
}