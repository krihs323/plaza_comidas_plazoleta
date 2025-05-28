package com.plaza.plazoleta.domain.api;

import com.plaza.plazoleta.domain.model.Menu;

public interface IMenuServicePort {

    void saveMenu(Menu menu);

    void updateMenu(Long id, Menu menu);
}