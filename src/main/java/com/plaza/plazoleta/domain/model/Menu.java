package com.plaza.plazoleta.domain.model;

public class Menu {

    private String name;

    private Long price;

    private String description;

    private String urlLogo;

    private Long categoryId;

    private Long restaurantId;

    public Menu(String name, Long price, String description, String urlLogo, Long categoriId, Long restaurantId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.urlLogo = urlLogo;
        this.categoryId = categoriId;
        this.restaurantId = restaurantId;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
