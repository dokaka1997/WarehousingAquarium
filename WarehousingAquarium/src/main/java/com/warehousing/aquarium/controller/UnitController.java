package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.model.response.UnitDTO;
import com.warehousing.aquarium.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("unit")
public class UnitController {

    UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<List<UnitDTO>> getAllCategory() {
        return ResponseEntity.ok(unitService.getAllUnit());
    }

    @PostMapping
    public ResponseEntity<Boolean> addNewUnit(@RequestBody UnitDTO unitDTO) {
        return ResponseEntity.ok(unitService.addnewUnit(unitDTO));
    }
}
