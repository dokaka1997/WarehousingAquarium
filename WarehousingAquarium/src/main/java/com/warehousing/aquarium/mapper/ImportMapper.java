package com.warehousing.aquarium.mapper;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.entity.BranchEntity;
import com.warehousing.aquarium.entity.ImportEntity;
import com.warehousing.aquarium.entity.SupplierEntity;
import com.warehousing.aquarium.model.response.ImportDTO;
import com.warehousing.aquarium.model.response.SupplierDTO;
import com.warehousing.aquarium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportMapper {
    @Autowired
    UserRepository userRepository;


    public static ImportDTO mapImportEntityToDTO(ImportEntity importEntity,
                                                 BranchEntity branchEntity,
                                                 SupplierEntity supplierEntity,
                                                 AccountEntity accountEntity) {
        ImportDTO importDTO = new ImportDTO();
        importDTO.setImportID(importEntity.getImportID());
        importDTO.setImportTime(importEntity.getImportTime());
        importDTO.setImportPrice(importEntity.getImportPrice());
        if (branchEntity != null) {
            importDTO.setBranch(branchEntity.getBranchName());
        }
        if (supplierEntity != null) {
            ImportMapper importMapper = new ImportMapper();
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
            supplierDTO.setDescription(supplierEntity.getDescription());
            if (supplierEntity.getUserId() != null && importMapper.userRepository.findById(supplierEntity.getUserId()).isPresent()) {
                AccountEntity account = importMapper.userRepository.findById(supplierEntity.getUserId()).get();
                supplierDTO.setUser(account.getName());
            }
            importDTO.setSupplierId(supplierDTO);
        }
        if (accountEntity != null) {
            importDTO.setUser(accountEntity.getName());
        }
        if (importEntity.getImportPrice() != null) {
            importDTO.setImportPrice(importEntity.getImportPrice());
        }
        importDTO.setSttStore(importEntity.getSttStore());
        importDTO.setStatusPayment(importEntity.getStatusPayment());
        return importDTO;
    }

    public SupplierDTO mapSupplier(SupplierEntity supplierEntity, SupplierDTO supplierDTO) {
        if (supplierEntity.getUserId() != null && userRepository.findById(supplierEntity.getUserId()).isPresent()) {
            AccountEntity account = userRepository.findById(supplierEntity.getUserId()).get();
            supplierDTO.setUser(account.getName());
        }
        return supplierDTO;
    }
}
