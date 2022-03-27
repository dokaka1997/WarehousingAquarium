package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.model.request.ImportRequest;
import com.warehousing.aquarium.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Boolean> addImport(@RequestBody ImportRequest importList) {

        return ResponseEntity.ok(importService.addListImport(importList));
    }

}
