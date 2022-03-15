package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.UnitEntity;
import com.warehousing.aquarium.model.response.UnitDTO;
import com.warehousing.aquarium.repository.UnitRepository;
import com.warehousing.aquarium.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    UnitRepository unitRepository;

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public List<UnitDTO> getAllUnit() {
        List<UnitDTO> unitDTOS = new ArrayList<>();
        List<UnitEntity> unitEntities = unitRepository.findAll();
        for (UnitEntity unitEntity : unitEntities) {
            UnitDTO unitDTO = new UnitDTO();
            unitDTO.setUnitId(unitEntity.getUnitId());
            unitDTO.setUnitName(unitEntity.getUnitName());
            unitDTOS.add(unitDTO);
        }
        return unitDTOS;
    }
}
