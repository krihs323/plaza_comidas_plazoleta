package com.plaza.plazoleta.infraestructure.output.client.entity;

import com.plaza.plazoleta.domain.model.Status;

import java.util.Date;

public class TraceabilityEntity {

    private Long idOrder;

    private Long idCustomer;

    private Status statusBefore;

    private Status status;

    private Date dateTime;

    private String nameCustomer;

    private String restaurantName;

    private Long idRestaurant;

    private String employeeName;

    private Long idEmployee;

    public TraceabilityEntity(Long idOrder, Long idCustomer, Status statusBefore, Status status, Date dateTime, String nameCustomer, String restaurantName, Long idRestaurant, String employeeName, Long idEmployee) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.statusBefore = statusBefore;
        this.status = status;
        this.dateTime = dateTime;
        this.nameCustomer = nameCustomer;
        this.restaurantName = restaurantName;
        this.idRestaurant = idRestaurant;
        this.employeeName = employeeName;
        this.idEmployee = idEmployee;
    }

    public Long getIdOrder() {
       return idOrder;
    }

    public void setIdOrder(Long idOrder) {
            this.idOrder = idOrder;
    }

    public Long getIdCustomer() {
            return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
            this.idCustomer = idCustomer;
    }

    public Status getStatusBefore() {
            return statusBefore;
    }

    public void setStatusBefore(Status statusBefore) {
            this.statusBefore = statusBefore;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }
}
