package com.example.kzvda.menumanagementsystem.serverApi.RequestBodies;

public class AddDishRequest {

    private String type = "add_dish";
    private Integer rest_id;
    private String dish_name;
    private String description;
    private Integer price;
    private Integer onoff;

    public AddDishRequest(Integer rest_id, String dish_name, String description, Integer price, boolean onoff) {
        this.rest_id = rest_id;
        this.dish_name = dish_name;
        this.description = description;
        this.price = price;
        this.onoff = (onoff) ? 1 : 0;
    }
}
