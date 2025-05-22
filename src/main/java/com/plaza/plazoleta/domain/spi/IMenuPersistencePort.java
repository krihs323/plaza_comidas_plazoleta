package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Menu;

import java.util.Optional;

public interface IMenuPersistencePort {

    void saveMenu(Menu menu);

    void updateMenu(Long id, Menu menu);

    Optional<Menu> findById(Long id);
}
