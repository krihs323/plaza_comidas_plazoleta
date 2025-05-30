package com.plaza.plazoleta.domain.model;


public class OrderDetail {

    private Long idMenu;

    private Integer amount;

    public OrderDetail(Long idMenu, Integer amount) {
        this.idMenu = idMenu;
        this.amount = amount;
    }

    public Long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


}
