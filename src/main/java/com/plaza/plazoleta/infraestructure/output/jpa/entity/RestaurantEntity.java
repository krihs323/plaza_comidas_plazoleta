package com.plaza.plazoleta.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurantes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_restaurante")
    private String name;

    @Column(name = "nit")
    private Long numberId;

    @Column(name = "direccion")
    private String address;

    @Column(name = "telefono")
    private String phoneNumber;

    @Column(name = "url_logo")
    private String urlLogo;

    @Column(name = "id_usuario")
    private Integer userId;

}
