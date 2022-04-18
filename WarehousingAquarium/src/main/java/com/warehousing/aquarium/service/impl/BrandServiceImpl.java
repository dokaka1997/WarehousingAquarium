package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.BrandEntity;
import com.warehousing.aquarium.model.response.BrandDTO;
import com.warehousing.aquarium.repository.BrandRepository;
import com.warehousing.aquarium.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDTO> getAllBrand() {
        List<BrandDTO> brandDTOS = new ArrayList<>();
        List<BrandEntity> brandEntities = brandRepository.findAll();
        for (BrandEntity brand : brandEntities) {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setBrandId(brand.getBrandId());
            brandDTO.setBrandName(brand.getBrandName());
            brandDTOS.add(brandDTO);
        }
        return brandDTOS;
    }

    @Override
    public Boolean addNewBrand(BrandDTO brandDTO) {
        try {
            BrandEntity brand = new BrandEntity();
            brand.setBrandId(brandDTO.getBrandId());
            brand.setBrandName(brandDTO.getBrandName());
            brandRepository.save(brand);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
