package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.MenuDisableRequest;
import com.plaza.plazoleta.application.dto.MenuRequest;
import com.plaza.plazoleta.application.dto.MenuResponse;
import com.plaza.plazoleta.application.mapper.MenuRequestMapper;
import com.plaza.plazoleta.domain.api.IMenuServicePort;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MenuHandler implements IMenuHandler {

    private final IMenuServicePort menuServicePort;
    private final MenuRequestMapper menuRequestMapper;

    public MenuHandler(IMenuServicePort menuServicePort, MenuRequestMapper menuRequestMapper) {
        this.menuServicePort = menuServicePort;
        this.menuRequestMapper = menuRequestMapper;
    }


    @Override
    public void saveMenu(MenuRequest menuRequest) {
        Menu menu = menuRequestMapper.toMenu(menuRequest);
        menuServicePort.saveMenu(menu);
    }

    @Override
    public void updateMenu(Long id, MenuRequest menuRequest) {
        Menu menu = menuRequestMapper.toMenu(menuRequest);
        menuServicePort.updateMenu(id, menu);
    }

    @Override
    public void disableMenu(Long id, MenuDisableRequest menuDisableRequest) {
        Menu menu = menuRequestMapper.toMenu(menuDisableRequest);
        menuServicePort.disableMenu(id, menu);
    }

    @Override
    public Page<MenuResponse> getMenuByRestaurant(Long idRestaurant, String idCategory, int page, int size, String sortBy, String sortDir) {
        Page<Menu> menuList = menuServicePort.getMenuByRestaurant(idRestaurant, idCategory, page, size, sortBy, sortDir);
        return menuList.map(menuRequestMapper::toMenuListResponse);
    }
}
