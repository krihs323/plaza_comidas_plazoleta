package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.MenuDisableRequest;
import com.plaza.plazoleta.application.dto.MenuRequest;
import com.plaza.plazoleta.application.dto.MenuResponse;
import com.plaza.plazoleta.application.dto.RestaurantListResponse;
import org.springframework.data.domain.Page;

public interface IMenuHandler {

    void saveMenu(MenuRequest menuRequest);

    void updateMenu(Long id, MenuRequest menuRequest);

    void disableMenu(Long id, MenuDisableRequest menuDisableRequest);

    Page<MenuResponse> getMenuByRestaurant(Long idRestaurant, String idCategory, int page, int size, String sortBy, String sortDir);
}