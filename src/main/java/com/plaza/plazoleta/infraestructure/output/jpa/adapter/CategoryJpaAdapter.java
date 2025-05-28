package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.Category;
import com.plaza.plazoleta.domain.spi.ICategoryPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.CategoryValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.CategoryEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.ICategoryRepository;

import java.util.Optional;

public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    public CategoryJpaAdapter(ICategoryRepository categoryRepository, CategoryEntityMapper categoryEntityMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
    }


    @Override
    public Category getCategoryById(Long id) {

        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if (categoryEntity.isPresent()) {
            return categoryEntityMapper.toCategory(categoryEntity.orElseThrow());
        }
        throw new CategoryValidationException(ExceptionResponse.CATEGORY_VALIDATION_NOT_FOUND.getMessage());

    }


}

