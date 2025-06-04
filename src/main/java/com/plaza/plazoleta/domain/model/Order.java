package com.plaza.plazoleta.domain.model;


import java.util.List;

public class Order {

    private Long id;

    private User customer;

    private Restaurant restaurant;

    private Status status;

    private List<OrderDetail> orderDetailList;

    private Long employeeAsignedId;

    private String pin;

    public Order(Long id, User customer, Restaurant restaurant, Status status, List<OrderDetail> orderDetailList, Long employeeAsignedId, String pin) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.status = status;
        this.orderDetailList = orderDetailList;
        this.employeeAsignedId = employeeAsignedId;
        this.pin = pin;
    }

    public Long getEmployeeAsignedId() {
        return employeeAsignedId;
    }

    public void setEmployeeAsignedId(Long employeeAsignedId) {
        this.employeeAsignedId = employeeAsignedId;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }


}
