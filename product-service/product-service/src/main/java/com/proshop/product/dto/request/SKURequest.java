package com.proshop.product.dto.request;

import lombok.Data;

import java.util.Map;

@Data
public class SKURequest {
  private String skuCode;
  private Map<String, Object> specs;
  private Double price;
  private Double discountPrice;
  private Integer stock;
  private String barcode;
  private Boolean isActive;
}
