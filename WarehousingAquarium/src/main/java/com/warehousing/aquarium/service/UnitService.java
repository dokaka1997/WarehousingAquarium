package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.UnitEntity;
import com.warehousing.aquarium.model.response.UnitDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UnitService {

    List<UnitDTO> getAllUnit();

}
