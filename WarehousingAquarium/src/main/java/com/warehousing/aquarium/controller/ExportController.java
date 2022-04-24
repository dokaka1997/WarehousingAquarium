package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.model.request.ExportRequest;
import com.warehousing.aquarium.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Boolean> addExport(@RequestBody ExportRequest exportRequest) {
        return ResponseEntity.ok(exportService.addExport(exportRequest));
    }
}
