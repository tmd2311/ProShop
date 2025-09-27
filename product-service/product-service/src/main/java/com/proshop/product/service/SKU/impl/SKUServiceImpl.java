package com.proshop.product.service.SKU.impl;

import com.proshop.product.dto.request.SKURequest;
import com.proshop.product.dto.response.SKUResponse;
import com.proshop.product.entity.SKUEntity;
import com.proshop.product.repository.SKURepository;
import com.proshop.product.service.SKU.SKUService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SKUServiceImpl implements SKUService {

  private final SKURepository skuRepository;

  @Override
  public List<SKUResponse> getAllSKUs() {
    return skuRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
  }

  @Override
  public SKUResponse getSKU(UUID id) {
    SKUEntity sku = skuRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("SKU not found"));
    return toResponse(sku);
  }

  @Override
  @Transactional
  public SKUResponse createSKU(SKURequest request) {
    if (skuRepository.existsBySkuCode(request.getSkuCode())) {
      throw new RuntimeException("SKU code already exists");
    }
    SKUEntity sku = new SKUEntity();
    sku.setSkuCode(request.getSkuCode());
    sku.setSpecs(request.getSpecs());
    sku.setPrice(request.getPrice());
    sku.setDiscountPrice(request.getDiscountPrice());
    sku.setStock(request.getStock());
    sku.setBarcode(request.getBarcode());
    sku.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

    SKUEntity saved = skuRepository.save(sku);
    return toResponse(saved);
  }

  @Override
  @Transactional
  public SKUResponse updateSKU(UUID id, SKURequest request) {
    SKUEntity sku = skuRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("SKU not found"));
    sku.setSkuCode(request.getSkuCode());
    sku.setSpecs(request.getSpecs());
    sku.setPrice(request.getPrice());
    sku.setDiscountPrice(request.getDiscountPrice());
    sku.setStock(request.getStock());
    sku.setBarcode(request.getBarcode());
    sku.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

    SKUEntity updated = skuRepository.save(sku);
    return toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteSKU(UUID id) {
    SKUEntity sku = skuRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("SKU not found"));
    skuRepository.delete(sku);
  }

  private SKUResponse toResponse(SKUEntity sku) {
    SKUResponse response = new SKUResponse();
    response.setId(sku.getId());
    response.setSkuCode(sku.getSkuCode());
    response.setSpecs(sku.getSpecs());
    response.setPrice(sku.getPrice());
    response.setDiscountPrice(sku.getDiscountPrice());
    response.setStock(sku.getStock());
    response.setBarcode(sku.getBarcode());
    response.setIsActive(sku.getIsActive());
    return response;
  }
}
