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
@RequestMapping("warehouse")
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
    public ResponseEntity<List<ProductBatchResponse>> getAllProductBatch(@PathVariable Long id) {
        return ResponseEntity.ok(productBatchService.getAllProductBatch(id));
    }
}
