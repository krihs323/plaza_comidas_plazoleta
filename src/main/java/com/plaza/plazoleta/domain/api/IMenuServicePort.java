package com.plaza.plazoleta.domain.api;

import com.plaza.plazoleta.application.dto.MenuRequest;
import com.plaza.plazoleta.domain.model.Menu;
import org.springframework.data.domain.Page;

public interface IMenuServicePort {

    void saveMenu(Menu menu);

    void updateMenu(Long id, Menu menu);

    void disableMenu(Long id, Menu menu);

    Page<Menu> getMenuByRestaurant(Long idRestaurant, String idCategory, int page, int size, String sortBy, String sortDir);
}