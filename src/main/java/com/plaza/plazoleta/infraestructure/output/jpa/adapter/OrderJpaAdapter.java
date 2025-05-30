package com.plaza.plazoleta.infraestructure.output.jpa.adapter;

import com.plaza.plazoleta.domain.api.IOrderPersistencePort;
import com.plaza.plazoleta.domain.model.Order;
import com.plaza.plazoleta.domain.model.OrderDetail;
import com.plaza.plazoleta.domain.model.Status;
import com.plaza.plazoleta.infraestructure.exception.MenuNotFoundException;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.MenuEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderDetailEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.entity.OrderEntity;
import com.plaza.plazoleta.infraestructure.output.jpa.mapper.OrderEntityMapper;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IMenuRepository;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IOrderDetailRepository;
import com.plaza.plazoleta.infraestructure.output.jpa.repository.IOrderRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final IOrderDetailRepository orderDetailRepository;
    private final IMenuRepository menuRepository;


    public OrderJpaAdapter(IOrderRepository orderRepository, OrderEntityMapper orderEntityMapper, IOrderDetailRepository orderDetailRepository, IMenuRepository menuRepository) {
        this.orderRepository = orderRepository;
        this.orderEntityMapper = orderEntityMapper;
        this.orderDetailRepository = orderDetailRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void saveOrder(Order order) {

        OrderEntity orderSaved = orderRepository.save(orderEntityMapper.toEntity(order));
        //Grabar el detalle

        List<OrderDetailEntity> detalles = new java.util.ArrayList<>();
        //Setear el id del pedido
        for (OrderDetail req : order.getOrderDetailList()) {
            //Buscar menuEntity
            MenuEntity menuEntity = menuRepository.findById(req.getIdMenu())
                    .orElseThrow(() -> new EntityNotFoundException("Plato con ID " + req.getIdMenu() + " no encontrado."));
            OrderDetailEntity detailEntity = new OrderDetailEntity();
            detailEntity.setOrderEntity(orderSaved);
            detailEntity.setMenuEntity(menuEntity);
            detailEntity.setAmount(req.getAmount());
            // precioUnitario will be set by @PrePersist in PedidoDetalle
            detalles.add(detailEntity);
        }

        orderDetailRepository.saveAll(detalles);

    }

    @Override
    public Optional<Order> findByCustomerAndStatusInvalid(Long idCustomer, List<Status> invalidStatusList) {
        Optional<OrderEntity> orderEntity = orderRepository.findByCustomerIdAndStatusIn(idCustomer, invalidStatusList);

        return Optional.ofNullable(orderEntityMapper.toOrder(orderEntity.orElse(null)));

    }
}