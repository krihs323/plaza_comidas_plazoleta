package com.plaza.plazoleta.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "platos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_plato")
    private String name;

    @Column(name = "precio")
    private Long price;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "url_logo")
    private String urlLogo;

    @Column(name = "id_categoria")
    private Integer categoryId;

    //@ManyToOne
    //@JoinColumn(name = "id_restaurante")
    //private RestaurantEntity restaurantEntity;
    @Column(name = "id_restaurante")
    private Integer restaurantId;
}
