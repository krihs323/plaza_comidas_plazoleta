package com.plaza.plazoleta.domain.model;

public class Menu {

    private Long id;

    private String name;

    private Long price;

    private String description;

    private String urlLogo;

    private Category category;

    private Restaurant restaurant;

    private Boolean active;

    public Menu() {
    }

    public Menu(Long id, String name, Long price, String description, String urlLogo, Category category, Restaurant restaurant, Boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.urlLogo = urlLogo;
        this.category = category;
        this.restaurant = restaurant;
        this.active = active == null || active;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


}