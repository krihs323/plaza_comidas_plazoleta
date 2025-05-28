package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.Category;
import com.plaza.plazoleta.infraestructure.exception.CategoryValidationException;
import com.plaza.plazoleta.infraestructure.exceptionhandler.ExceptionResponse;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCategoryById() {

        CategoryEntity categoryEntityMock = new CategoryEntity(1L, "Comida Tipica");
        Category categoryMock = new Category(1L, "Comida Tipica");

        Optional<CategoryEntity> categoryEntityFound = Optional.of(categoryEntityMock);
        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(categoryEntityFound);
        Mockito.when(categoryEntityMapper.toCategory(any())).thenReturn(categoryMock);

        Category categoryExpected = categoryJpaAdapter.getCategoryById(anyLong());

        assertEquals(categoryExpected.getId(), categoryMock.getId());

    }

    @Test
    void getCategoryByIdException() {

        Category categoryMock = new Category(1L, "Comida Tipica");

        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        Mockito.when(categoryEntityMapper.toCategory(any())).thenReturn(categoryMock);

        CategoryValidationException exception = assertThrows(CategoryValidationException.class, () ->
             categoryJpaAdapter.getCategoryById(anyLong())
        );

        assertEquals(ExceptionResponse.CATEGORY_VALIDATION_NOT_FOUND.getMessage(), exception.getMessage());

    }
}