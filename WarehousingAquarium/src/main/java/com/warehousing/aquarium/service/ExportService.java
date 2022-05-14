package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.ExportEntity;
import com.warehousing.aquarium.model.request.ExportRequest;

public interface ExportService {
    ExportEntity addExport(ExportRequest exportRequest);
}
