package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.CategoryEntity;
import com.warehousing.aquarium.repository.CategoryRepository;
import com.warehousing.aquarium.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAll();
    }
}
