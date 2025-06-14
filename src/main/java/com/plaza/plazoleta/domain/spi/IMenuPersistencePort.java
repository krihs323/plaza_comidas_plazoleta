package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.PageResult;

import java.util.Optional;

public interface IMenuPersistencePort {

    void saveMenu(Menu menu);

    Optional<Menu> findById(Long id);

    PageResult<Menu> getMenuByRestaurant(Long idRestaurant, Long idCategory, int page, int size, String sortBy, String sortDir);
}
