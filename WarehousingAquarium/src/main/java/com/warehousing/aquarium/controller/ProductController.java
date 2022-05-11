package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.model.request.ProductRequest;
import com.warehousing.aquarium.model.response.ProductDTO;
import com.warehousing.aquarium.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam int pageIndex, @RequestParam int pageSize, @RequestParam String search) {
        return ResponseEntity.ok(productService.getAllProducts(pageIndex, pageSize, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Boolean> createProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProdcut(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProductById(id));
    }

    @DeleteMapping("list")
    public ResponseEntity<Boolean> deleteListProdcutByIds(@RequestBody List<Long> ids) {
        for (Long id : ids) {
            productService.deleteProductById(id);
        }
        return ResponseEntity.ok(true);
    }

    @PutMapping
    public ResponseEntity<Boolean> updateListProduct(@RequestBody List<ProductRequest> productRequest) {
        return ResponseEntity.ok(productService.updateListProduct(productRequest));
    }

}
