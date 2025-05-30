package com.plaza.plazoleta.domain.model;


import java.util.List;

public class Order {

    private Long id;

    private User customer;

    private Restaurant restaurant;

    private Status status;

    private List<OrderDetail> orderDetailList;

    public Order(Long id, User customer, Restaurant restaurant, Status status, List<OrderDetail> orderDetailList) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.status = status;
        this.orderDetailList = orderDetailList;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", restaurant=" + restaurant +
                ", status=" + status +
                '}';
    }
}
