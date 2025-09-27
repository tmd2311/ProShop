package com.proshop.product.dto.response;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchResponse {
  private UUID id;
  private String name;
  private String description;
  private String categoryName;
  private String brandName;
  private BigDecimal minPrice;
  private BigDecimal maxPrice;
  private String thumbnailImage;
  private LocalDateTime createdAt;
}
