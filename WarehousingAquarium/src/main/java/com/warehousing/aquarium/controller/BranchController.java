package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.entity.BranchEntity;
import com.warehousing.aquarium.model.response.BranchDTO;
import com.warehousing.aquarium.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("branch")
public class BranchController {

    BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<BranchEntity>> getAllBranch() {
        return ResponseEntity.ok(branchService.getAllBranch());
    }

    @PostMapping
    public ResponseEntity<Boolean> addNewBranch(@RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.addNewBranch(branchDTO));
    }

}
