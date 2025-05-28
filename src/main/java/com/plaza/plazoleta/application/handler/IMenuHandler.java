package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.MenuDisableRequest;
import com.plaza.plazoleta.application.dto.MenuRequest;

public interface IMenuHandler {

    void saveMenu(MenuRequest menuRequest);

    void updateMenu(Long id, MenuRequest menuRequest);

    void disableMenu(Long id, MenuDisableRequest menuDisableRequest);
}