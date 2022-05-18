package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.entity.ImportEntity;
import com.warehousing.aquarium.model.request.ImportRequest;
import com.warehousing.aquarium.model.response.ImportDTO;
import com.warehousing.aquarium.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("import")
public class ImportController {

    ImportService importService;

    @Autowired
    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping
    public ResponseEntity<ImportEntity> addImport(@RequestBody ImportRequest importList) {
        return ResponseEntity.ok(importService.addListImport(importList));
    }

    @GetMapping
    public ResponseEntity<List<ImportDTO>> getAllImport(@RequestParam int pageIndex, @RequestParam int pageSize, @RequestParam String search) {
        int id = 0;
        try {
            id = Integer.parseInt(search);
        } catch (Exception exception) {
            id = 0;
        }
        return ResponseEntity.ok(importService.getAllImport(pageIndex, pageSize, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImportDTO> getImportById(@PathVariable Long id) {
        return ResponseEntity.ok(importService.getImportById(id));
    }

    @GetMapping("supplier")
    public ResponseEntity<List<ImportDTO>> getImportBySupplierId(@RequestParam int pageIndex, @RequestParam int pageSize, @RequestParam String supplierId) {
        int id = 0;
        try {
            id = Integer.parseInt(supplierId);
        } catch (Exception exception) {
            id = 0;
        }
        return ResponseEntity.ok(importService.getImportBySupplierId(pageIndex, pageSize, id));
    }

}
