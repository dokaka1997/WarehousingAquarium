package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.SupplierEntity;
import com.warehousing.aquarium.model.response.SupplierDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SupplierService {
    List<SupplierDTO> getAllSupplier();
}
