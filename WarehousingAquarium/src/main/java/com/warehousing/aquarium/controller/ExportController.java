package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.entity.ExportEntity;
import com.warehousing.aquarium.model.request.ExportRequest;
import com.warehousing.aquarium.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("export")
public class ExportController {
    ExportService exportService;

    @Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @PostMapping
    public ResponseEntity<ExportEntity> addExport(@RequestBody ExportRequest exportRequest) {
        return ResponseEntity.ok(exportService.addExport(exportRequest));
    }

    @GetMapping
    public ResponseEntity<List<ExportEntity>> getAllExport(@RequestParam int pageIndex, @RequestParam int pageSize) {

        return ResponseEntity.ok(exportService.getAllExport(pageIndex, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExportEntity> getImportById(@PathVariable Long id) {
        return ResponseEntity.ok(exportService.getExportById(id));
    }
}
