package com.proshop.product.dto.response;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class SKUResponse {
  private UUID id;
  private String skuCode;
  private Map<String, Object> specs;
  private Double price;
  private Double discountPrice;
  private Integer stock;
  private String barcode;
  private Boolean isActive;
}
