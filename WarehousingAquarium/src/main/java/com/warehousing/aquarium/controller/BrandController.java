package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.model.response.BrandDTO;
import com.warehousing.aquarium.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("brand")
public class BrandController {
    BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrand() {
        return ResponseEntity.ok(brandService.getAllBrand());
    }

    @PostMapping
    public ResponseEntity<Boolean> addNewBrand(@RequestBody BrandDTO brandDTO){
        return ResponseEntity.ok(brandService.addNewBrand(brandDTO));
    }
}
