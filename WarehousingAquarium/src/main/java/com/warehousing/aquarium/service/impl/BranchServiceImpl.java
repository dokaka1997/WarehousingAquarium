package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.BranchEntity;
import com.warehousing.aquarium.repository.BranchRepository;
import com.warehousing.aquarium.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {
    BranchRepository branchRepository;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public List<BranchEntity> getAllBranch() {
        return branchRepository.findAll();
    }
}
