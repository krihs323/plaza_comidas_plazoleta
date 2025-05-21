package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.model.Menu;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.MenuEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IMenuRepository;

//@RequiredArgsConstructor
public class MenuJpaAdapter implements IMenuPersistencePort {

    private final IMenuRepository menuRepository;
    private final MenuEntityMapper menuEntityMapper;

    public MenuJpaAdapter(IMenuRepository menuRepository, MenuEntityMapper menuEntityMapper) {
        this.menuRepository = menuRepository;
        this.menuEntityMapper = menuEntityMapper;
    }



    @Override
    public void saveMenu(Menu menu) {
        //Antes de guardar



        menuRepository.save(menuEntityMapper.toEntity(menu));
    }
}
