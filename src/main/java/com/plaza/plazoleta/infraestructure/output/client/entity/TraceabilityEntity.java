package com.plaza.plazoleta.infraestructure.output.client.entity;

import com.plaza.plazoleta.domain.model.Status;

public class TraceabilityEntity {

    private Long idOrder;

    private Long idCustomer;

    private Status statusBefore;

    private Status status;

    public TraceabilityEntity(Long idOrder, Long idCustomer, Status statusBefore, Status status) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.statusBefore = statusBefore;
        this.status = status;
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
}
