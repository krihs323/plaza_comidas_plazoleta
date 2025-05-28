package com.plaza.plazoleta.application.handler;

import com.plaza.plazoleta.application.dto.MenuRequest;
import com.plaza.plazoleta.application.mapper.MenuRequestMapper;
import com.plaza.plazoleta.domain.api.IMenuServicePort;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Restaurant;
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
}
