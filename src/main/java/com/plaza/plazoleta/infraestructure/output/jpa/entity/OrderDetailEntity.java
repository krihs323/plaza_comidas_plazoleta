package com.plaza.plazoleta.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos_detalle")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "id_plato", nullable = false)
    MenuEntity menuEntity;

    @Column(name = "cantidad")
    private Integer amount;

}

