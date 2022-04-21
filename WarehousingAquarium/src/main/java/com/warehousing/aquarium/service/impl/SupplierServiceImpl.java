package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.SupplierEntity;
import com.warehousing.aquarium.model.response.SupplierDTO;
import com.warehousing.aquarium.repository.SupplierRepository;
import com.warehousing.aquarium.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<SupplierDTO> getAllSupplier() {
        List<SupplierDTO> supplierDTOS = new ArrayList<>();
        List<SupplierEntity> supplierEntities = supplierRepository.findAll();
        for (SupplierEntity supplierEntity : supplierEntities) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupplierId(supplierEntity.getSupplierId());
            supplierDTO.setSupplierCode(supplierEntity.getSupplierCode());
            supplierDTO.setSupplierName(supplierEntity.getSupplierName());
            supplierDTO.setEmail(supplierEntity.getEmail());
            supplierDTO.setGroup(supplierEntity.getGroup());
            supplierDTO.setPhone(supplierEntity.getPhone());
            supplierDTO.setStatus(supplierEntity.isStatus());
            supplierDTO.setTaxIdentificationNumber(supplierEntity.getTaxIdentificationNumber());
            supplierDTO.setDept(supplierEntity.getDept());
            supplierDTO.setAddress(supplierEntity.getAddress());
            supplierDTO.setUserId(supplierEntity.getUserId());
            supplierDTO.setDescription(supplierEntity.getDescription());
            supplierDTOS.add(supplierDTO);

        }
        return supplierDTOS;
    }

    @Override
    public Boolean addNewSupplier(SupplierDTO supplierDTO) {
        try {
            ModelMapper mapper = new ModelMapper();
            SupplierEntity entity = mapper.map(supplierDTO, SupplierEntity.class);
            supplierRepository.save(entity);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public Boolean deleteSupplier(Long id) {
        try {
            supplierRepository.deleteById(id);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
