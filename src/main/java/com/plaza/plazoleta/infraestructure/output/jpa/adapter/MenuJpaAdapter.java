package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.infraestructure.exception.MenuNotFoundException;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.MenuEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IMenuRepository;

import java.util.Optional;

public class MenuJpaAdapter implements IMenuPersistencePort {

    private final IMenuRepository menuRepository;
    private final MenuEntityMapper menuEntityMapper;

    public MenuJpaAdapter(IMenuRepository menuRepository, MenuEntityMapper menuEntityMapper) {
        this.menuRepository = menuRepository;
        this.menuEntityMapper = menuEntityMapper;
    }

    @Override
    public void saveMenu(Menu menu) {
        menuRepository.save(menuEntityMapper.toEntity(menu));
    }

    @Override
    public void updateMenu(Long id, Menu menu) {
        MenuEntity menuNew = menuEntityMapper.toEntity(menu);
        menuRepository.save(menuNew);
    }

    @Override
    public Optional<Menu> findById(Long id) {

        Optional<MenuEntity> menuEntity = menuRepository.findById(id);
        return Optional.ofNullable(menuEntityMapper.toMenu(menuEntity.orElseThrow(() -> new MenuNotFoundException("No se encontró el Plato que intenta modificar"))));

    }
}