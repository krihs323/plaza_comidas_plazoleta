package com.plaza.plazoleta.domain.model;


public class OrderReport {

    private Integer employeeId;

    private Number avg;

    private Integer orderId;
    private Integer customerId;
    private String status;
    private Number time;

    public OrderReport() {
    }

    public OrderReport(Integer employeeId, Number avg) {
        this.employeeId = employeeId;
        this.avg = avg;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Number getAvg() {
        return avg;
    }

    public void setAvg(Number avg) {
        this.avg = avg;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Number getTime() {
        return time;
    }

    public void setTime(Number time) {
        this.time = time;
    }
}


