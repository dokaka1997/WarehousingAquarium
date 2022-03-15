package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.entity.SupplierEntity;
import com.warehousing.aquarium.model.response.SupplierDTO;
import com.warehousing.aquarium.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("supplier")
public class SupplierController {

    SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSupplier() {
        return ResponseEntity.ok(supplierService.getAllSupplier());
    }
}
