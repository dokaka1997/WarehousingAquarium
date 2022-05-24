package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.entity.ProductBatchEntity;
import com.warehousing.aquarium.model.response.ProductBatchResponse;
import com.warehousing.aquarium.service.ProductBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("product_batch")
public class ProductBatchController {

    ProductBatchService productBatchService;

    @Autowired
    public ProductBatchController(ProductBatchService productBatchService) {
        this.productBatchService = productBatchService;
    }

    @PostMapping
    public ResponseEntity<ProductBatchEntity> addNewProductBatch(@RequestBody ProductBatchEntity productBatchEntity) {
        return ResponseEntity.ok(productBatchService.addNewProductBatch(productBatchEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductBatchResponse>> getAllProductBatchById(@PathVariable Long id) {
        return ResponseEntity.ok(productBatchService.getAllProductBatchById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ProductBatchResponse>> getAllProductBatch() {
        return ResponseEntity.ok(productBatchService.getAllProductBatch());
    }
}
