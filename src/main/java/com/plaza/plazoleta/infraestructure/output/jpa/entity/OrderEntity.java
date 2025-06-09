package com.plaza.plazoleta.infraestructure.output.jpa.entity;

import com.plaza.plazoleta.domain.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente")
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "id_restaurante")
    private RestaurantEntity restaurantEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    Status status;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> details = new ArrayList<>();

    @Column(name = "id_empleado")
    private Long employeeAsignedId;

    @Column(name = "pin")
    private String pin;

    @Column(name = "fecha_inicio")
    private Date startDate;

    @Column(name = "fecha_fin")
    private Date endDate;

}

