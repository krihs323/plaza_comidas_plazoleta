package com.plaza.plazoleta.infraestructure.configuration;

import com.plaza.plazoleta.domain.api.IMenuServicePort;
import com.plaza.plazoleta.domain.api.IRestaurantServicePort;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.domain.usercase.MenuUserCase;
import com.plaza.plazoleta.domain.usercase.RestaurantUserCase;
import com.plaza.plazoleta.infraestructure.output.client.adapter.UserClientAdapter;
import com.plaza.plazoleta.infraestructure.output.client.mapper.UserEntityMapper;
import com.plaza.plazoleta.infraestructure.output.client.repository.IUserFeignClient;
import com.plaza.plazoleta.infraestructure.output.jpa.adapter.MenuJpaAdapter;
import com.plaza.plazoleta.infraestructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.MenuEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.RestaurantEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IMenuRepository;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IRestaurantRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
//@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    private final IUserFeignClient userFeignClient;
    private final UserEntityMapper userEntityMapper;

    private final IMenuRepository menuRepository;
    private final MenuEntityMapper menuEntityMapper;



    public BeanConfiguration(IRestaurantRepository restaurantRepository, RestaurantEntityMapper restaurantEntityMapper, IUserFeignClient userFeignClient, UserEntityMapper userEntityMapper, IMenuRepository menuRepository, MenuEntityMapper menuEntityMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantEntityMapper = restaurantEntityMapper;
        this.userFeignClient = userFeignClient;
        this.userEntityMapper = userEntityMapper;
        this.menuRepository = menuRepository;
        this.menuEntityMapper = menuEntityMapper;
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IUserPersistencePort userPersistencePort(){
        return new UserClientAdapter(userFeignClient, userEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUserCase(restaurantPersistencePort(), userPersistencePort());
    }

    @Bean
    public IMenuPersistencePort menuPersistencePort(){
        return new MenuJpaAdapter(menuRepository, menuEntityMapper);
    }

    @Bean
    public IMenuServicePort menuServicePort(){
        return new MenuUserCase(menuPersistencePort(), restaurantPersistencePort(), userPersistencePort());
    }

}
