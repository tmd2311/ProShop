package com.proshop.product.controller;

import com.proshop.product.dto.request.ProductCreateRequest;
import com.proshop.product.dto.request.ProductSearchRequest;
import com.proshop.product.dto.request.ProductUpdateRequest;
import com.proshop.product.dto.response.PageResponse;
import com.proshop.product.dto.response.ProductDeleteResponse;
import com.proshop.product.dto.response.GeneralResponse;
import com.proshop.product.dto.response.ProductResponse;
import com.proshop.product.service.product.ProductService;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/product")
    public ResponseEntity<GeneralResponse<PageResponse<ProductResponse>>> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "12") int size,
        @RequestParam(required = false) String sortField,
        @RequestParam(required = false) String sortDirection) {

        GeneralResponse<PageResponse<ProductResponse>> response = productService.getProducts(page, size, sortField, sortDirection);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<GeneralResponse<ProductResponse>> getProductById(@PathVariable("id") String idStr) {
        return ResponseEntity.ok(productService.getProductById(idStr));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GeneralResponse<ProductDeleteResponse>> deleteProduct(@RequestParam("id") String idStr) {
        GeneralResponse<ProductDeleteResponse> response = productService.deleteProduct(idStr);
        if (response.getStatus().getCode().equals("404")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<GeneralResponse<ProductResponse>> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductUpdateRequest request) {
        GeneralResponse<ProductResponse> response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/search")
    public GeneralResponse<PageResponse<ProductResponse>> searchProducts(
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "categoryIds", required = false) List<String> categoryIds,
        @RequestParam(value = "brandIds", required = false) List<String> brandIds,
        @RequestParam(value = "minPrice", required = false) Long minPrice,
        @RequestParam(value = "maxPrice", required = false) Long maxPrice,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        ProductSearchRequest request = new ProductSearchRequest();
        request.setKeyword(keyword);
        if (categoryIds != null) {
            request.setCategoryIds(categoryIds.stream().map(UUID::fromString).toList());
        }
        if (brandIds != null) {
            request.setBrandIds(brandIds.stream().map(UUID::fromString).toList());
        }
        if (minPrice != null) {
            request.setMinPrice(BigDecimal.valueOf(minPrice));
        }
        if (maxPrice != null) {
            request.setMaxPrice(BigDecimal.valueOf(maxPrice));
        }
        return productService.searchProductsV2(request, page, size);
    }

    @PostMapping("/product/create")
    public ResponseEntity<GeneralResponse<ProductResponse>> createProduct(
            @RequestBody ProductCreateRequest request) {
        GeneralResponse<ProductResponse> response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
