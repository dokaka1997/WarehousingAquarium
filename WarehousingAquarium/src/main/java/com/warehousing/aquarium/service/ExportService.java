package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.ExportEntity;
import com.warehousing.aquarium.model.request.ExportRequest;
import com.warehousing.aquarium.model.response.ExportDTO;

import java.util.List;

public interface ExportService {
    ExportEntity addExport(ExportRequest exportRequest);

    List<ExportEntity> getAllExport(int pageIndex, int pageSize);

    ExportEntity getExportById(Long id);

}
