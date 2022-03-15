package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.CategoryEntity;
import com.warehousing.aquarium.model.response.CategoryDTO;
import com.warehousing.aquarium.repository.CategoryRepository;
import com.warehousing.aquarium.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        for(CategoryEntity categoryEntity : categoryEntities){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryId(categoryEntity.getCategoryId());
            categoryDTO.setCategoryName(categoryEntity.getCategoryName());
            categoryDTO.setCreateDate(categoryEntity.getCreateDate());
            categoryDTOS.add(categoryDTO);
        }

        return categoryDTOS;
    }
}
