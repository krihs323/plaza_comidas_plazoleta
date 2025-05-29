package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Menu;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IMenuPersistencePort {

    void saveMenu(Menu menu);

    void updateMenu(Long id, Menu menu);

    Optional<Menu> findById(Long id);

    Page<Menu> getMenuByRestaurant(Long idRestaurant, Long idCategory, int page, int size, String sortBy, String sortDir);
}
