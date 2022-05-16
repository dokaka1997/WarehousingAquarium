package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.entity.WarehouseEntity;
import com.warehousing.aquarium.model.response.WarehouseResponse;
import com.warehousing.aquarium.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("warehouse")
public class WarehouseController {

    WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<WarehouseEntity> addNewWarehouse(@RequestBody WarehouseEntity warehouseEntity) {
        return ResponseEntity.ok(warehouseService.addNewWarehouse(warehouseEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouse(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getAllWarehouse(id));
    }
}
