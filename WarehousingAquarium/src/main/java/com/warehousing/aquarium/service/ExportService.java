package com.warehousing.aquarium.service;

import com.warehousing.aquarium.model.request.ExportRequest;

public interface ExportService {
    Boolean addExport(ExportRequest exportRequest);
}
