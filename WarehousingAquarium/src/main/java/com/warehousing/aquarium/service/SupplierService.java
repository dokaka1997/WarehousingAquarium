package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.SupplierEntity;
import com.warehousing.aquarium.model.response.SupplierDTO;

import java.util.List;


public interface SupplierService {
    List<SupplierDTO> getAllSupplier();

    SupplierEntity addNewSupplier(SupplierDTO supplierDTO);

    Boolean deleteSupplier(Long id);

    SupplierDTO getSupplierById(Long id);
}
