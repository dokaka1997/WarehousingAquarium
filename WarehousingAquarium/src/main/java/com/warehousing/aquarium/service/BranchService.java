package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.BranchEntity;
import com.warehousing.aquarium.model.response.BranchDTO;

import java.util.List;

public interface BranchService {
    List<BranchEntity> getAllBranch();
    Boolean addNewBranch(BranchDTO branchDTO);
}
