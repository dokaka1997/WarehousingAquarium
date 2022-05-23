package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.ProductBatchEntity;
import com.warehousing.aquarium.model.response.ProductBatchResponse;

import java.util.List;

public interface ProductBatchService {

    ProductBatchEntity addNewProductBatch(ProductBatchEntity productBatchEntity);

    List<ProductBatchResponse> getAllProductBatchById(Long productId);

    List<ProductBatchResponse> getAllProductBatch();

}
