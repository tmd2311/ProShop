package com.proshop.product.controller;

import com.proshop.product.dto.request.SKURequest;
import com.proshop.product.dto.response.SKUResponse;
import com.proshop.product.service.SKU.SKUService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sku")
@RequiredArgsConstructor
public class SKUController {

  private final SKUService skuService;

  @GetMapping
  public ResponseEntity<List<SKUResponse>> getAllSKUs() {
    return ResponseEntity.ok(skuService.getAllSKUs());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SKUResponse> getSKU(@PathVariable UUID id) {
    return ResponseEntity.ok(skuService.getSKU(id));
  }

  @PostMapping
  public ResponseEntity<SKUResponse> createSKU(@RequestBody SKURequest request) {
    return ResponseEntity.ok(skuService.createSKU(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<SKUResponse> updateSKU(@PathVariable UUID id, @RequestBody SKURequest request) {
    return ResponseEntity.ok(skuService.updateSKU(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSKU(@PathVariable UUID id) {
    skuService.deleteSKU(id);
    return ResponseEntity.noContent().build();
  }
}
