package com.warehousing.aquarium.service;

import com.warehousing.aquarium.model.request.ImportRequest;

import java.util.List;

public interface ImportService {
    boolean addListImport(ImportRequest importList);
}
