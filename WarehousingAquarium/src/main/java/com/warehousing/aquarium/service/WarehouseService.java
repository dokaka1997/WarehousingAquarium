package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.WarehouseEntity;
import com.warehousing.aquarium.model.response.WarehouseResponse;

import java.util.List;

public interface WarehouseService {

    WarehouseEntity addNewWarehouse(WarehouseEntity warehouseEntity);

    List<WarehouseResponse> getAllWarehouse(Long productId);

}
