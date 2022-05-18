package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.ImportEntity;
import com.warehousing.aquarium.model.request.ImportRequest;
import com.warehousing.aquarium.model.response.ImportDTO;

import java.util.List;

public interface ImportService {
    ImportEntity addListImport(ImportRequest importList);

    List<ImportDTO> getAllImport(int pageIndex, int pageSize, int search);

    ImportDTO getImportById(Long id);

    List<ImportDTO> getImportBySupplierId(int pageIndex, int pageSize,int id);
}
