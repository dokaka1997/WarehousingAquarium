package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.BrandEntity;
import com.warehousing.aquarium.model.response.BrandDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BrandService {
    List<BrandDTO> getAllBrand();
}
