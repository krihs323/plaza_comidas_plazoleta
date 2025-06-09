package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.Category;
import com.plaza.plazoleta.infraestructure.exception.CategoryValidationException;
import com.plaza.plazoleta.domain.exception.ExceptionResponse;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.CategoryEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    private Category category;

    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(1L, "Comida Tipica");

        categoryEntity = new CategoryEntity(1L, "Comida Tipica");

    }

    @Test
    void getCategoryById() {

        Optional<CategoryEntity> categoryEntityFound = Optional.of(categoryEntity);
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(categoryEntityFound);
        Mockito.when(categoryEntityMapper.toCategory(any())).thenReturn(category);

        Category categoryExpected = categoryJpaAdapter.getCategoryById(anyLong());

        assertEquals(categoryExpected.getId(), category.getId());

    }

    @Test
    void getCategoryByIdException() {


        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(categoryEntityMapper.toCategory(any())).thenReturn(category);

        CategoryValidationException exception = assertThrows(CategoryValidationException.class, () ->
             categoryJpaAdapter.getCategoryById(anyLong())
        );

        assertEquals(ExceptionResponse.CATEGORY_VALIDATION_NOT_FOUND.getMessage(), exception.getMessage());

    }
}