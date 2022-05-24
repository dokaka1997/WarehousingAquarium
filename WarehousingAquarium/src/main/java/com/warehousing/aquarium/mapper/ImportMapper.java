package com.warehousing.aquarium.mapper;

import com.warehousing.aquarium.entity.AccountEntity;
import com.warehousing.aquarium.entity.BranchEntity;
import com.warehousing.aquarium.entity.ImportEntity;
import com.warehousing.aquarium.entity.SupplierEntity;
import com.warehousing.aquarium.model.response.ImportDTO;

public class ImportMapper {

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
            importDTO.setSupplierId(supplierEntity);
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
}
