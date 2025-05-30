package com.plaza.plazoleta.infraestructure.configuration;

import com.plaza.plazoleta.domain.api.IMenuServicePort;
import com.plaza.plazoleta.domain.api.IOrderPersistencePort;
import com.plaza.plazoleta.domain.api.IOrderServicePort;
import com.plaza.plazoleta.domain.api.IRestaurantServicePort;
import com.plaza.plazoleta.domain.spi.ICategoryPersistencePort;
import com.plaza.plazoleta.domain.spi.IMenuPersistencePort;
import com.plaza.plazoleta.domain.spi.IRestaurantPersistencePort;
import com.plaza.plazoleta.domain.spi.IUserPersistencePort;
import com.plaza.plazoleta.domain.usercase.MenuUserCase;
import com.plaza.plazoleta.domain.usercase.OrderUserCase;
import com.plaza.plazoleta.domain.usercase.RestaurantUserCase;
import com.plaza.plazoleta.infraestructure.output.client.adapter.UserClientAdapter;
import com.plaza.plazoleta.infraestructure.output.client.mapper.UserEntityMapper;
import com.plaza.plazoleta.infraestructure.output.client.repository.IUserFeignClient;
import com.plaza.plazoleta.infraestructure.output.jpa.adapter.CategoryJpaAdapter;
import com.plaza.plazoleta.infraestructure.output.jpa.adapter.MenuJpaAdapter;
import com.plaza.plazoleta.infraestructure.output.jpa.adapter.OrderJpaAdapter;
import com.plaza.plazoleta.infraestructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.CategoryEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.MenuEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.OrderEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.RestaurantEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.*;
import com.plaza.plazoleta.infraestructure.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    private final IUserFeignClient userFeignClient;
    private final UserEntityMapper userEntityMapper;

    private final IMenuRepository menuRepository;
    private final MenuEntityMapper menuEntityMapper;

    private final HttpServletRequest httpServletRequest;


    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    private final JwtService jwtService;

    private final IOrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final IOrderDetailRepository orderDetailRepository;

    public BeanConfiguration(IRestaurantRepository restaurantRepository, RestaurantEntityMapper restaurantEntityMapper, IUserFeignClient userFeignClient, UserEntityMapper userEntityMapper, IMenuRepository menuRepository, MenuEntityMapper menuEntityMapper, HttpServletRequest httpServletRequest, ICategoryRepository categoryRepository, CategoryEntityMapper categoryEntityMapper, JwtService jwtService, IOrderRepository orderRepository, OrderEntityMapper orderEntityMapper, IOrderDetailRepository orderDetailRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantEntityMapper = restaurantEntityMapper;
        this.userFeignClient = userFeignClient;
        this.userEntityMapper = userEntityMapper;
        this.menuRepository = menuRepository;
        this.menuEntityMapper = menuEntityMapper;
        this.httpServletRequest = httpServletRequest;
        this.categoryRepository = categoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
        this.jwtService = jwtService;
        this.orderRepository = orderRepository;
        this.orderEntityMapper = orderEntityMapper;
        this.orderDetailRepository = orderDetailRepository;
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
        return new RestaurantUserCase(restaurantPersistencePort(), userPersistencePort(), httpServletRequest);
    }

    @Bean
    public IMenuPersistencePort menuPersistencePort(){
        return new MenuJpaAdapter(menuRepository, menuEntityMapper);
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public IMenuServicePort menuServicePort(){
        return new MenuUserCase(menuPersistencePort(), restaurantPersistencePort(), userPersistencePort(), categoryPersistencePort(), httpServletRequest, jwtService);

    }

    //Hu11
    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderRepository, orderEntityMapper, orderDetailRepository, menuRepository);
    }

    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUserCase(orderPersistencePort(), httpServletRequest, jwtService, userPersistencePort());
    }

}
