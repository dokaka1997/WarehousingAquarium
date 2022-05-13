package com.warehousing.aquarium.mapper;

import com.warehousing.aquarium.entity.*;
import com.warehousing.aquarium.model.response.ImportDTO;

import java.util.List;
import java.util.Objects;

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
            importDTO.setSupplier(supplierEntity.getSupplierName());
            importDTO.setSupplierId(supplierEntity.getSupplierId());
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
