package com.warehousing.aquarium.service;

import com.warehousing.aquarium.model.response.BrandDTO;

import java.util.List;

public interface BrandService {
    List<BrandDTO> getAllBrand();

    Boolean addNewBrand(BrandDTO brandDTO);
}
