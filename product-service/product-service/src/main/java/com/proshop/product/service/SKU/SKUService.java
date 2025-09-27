package com.proshop.product.service.SKU;

import com.proshop.product.dto.request.SKURequest;
import com.proshop.product.dto.response.SKUResponse;
import java.util.List;
import java.util.UUID;

public interface SKUService {

  List<SKUResponse> getAllSKUs();
  SKUResponse getSKU(UUID id);
  SKUResponse createSKU(SKURequest request);
  SKUResponse updateSKU(UUID id, SKURequest request);
  void deleteSKU(UUID id);
}
