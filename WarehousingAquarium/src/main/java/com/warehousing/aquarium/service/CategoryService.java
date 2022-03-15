package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.CategoryEntity;
import com.warehousing.aquarium.model.response.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategory();
}
