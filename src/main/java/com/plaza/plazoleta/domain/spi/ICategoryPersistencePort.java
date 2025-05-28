package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Category;

public interface ICategoryPersistencePort {

    Category getCategoryById(Long id);
}