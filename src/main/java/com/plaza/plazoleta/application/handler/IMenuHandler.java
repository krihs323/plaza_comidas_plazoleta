package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.*;
import com.plaza.plazoleta.domain.model.PageResult;

public interface IMenuHandler {

    void saveMenu(MenuRequest menuRequest);

    void updateMenu(Long id, MenuUpdateRequest menuRequest);

    void disableMenu(Long id, MenuDisableRequest menuDisableRequest);

    PageResult<MenuResponse> getMenuByRestaurant(Long idRestaurant, String idCategory, int page, int size, String sortBy, String sortDir);
}