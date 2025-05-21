package com.plaza.plazoleta.domain.spi;

import com.plaza.plazoleta.domain.model.Menu;

public interface IMenuPersistencePort {

    void saveMenu(Menu menu);
}
