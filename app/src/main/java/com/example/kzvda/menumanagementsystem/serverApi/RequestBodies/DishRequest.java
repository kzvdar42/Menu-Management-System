package com.example.kzvda.menumanagementsystem.serverApi.RequestBodies;

public class DishRequest {

    private String type = "add_dish";
    private Integer rest_id;
    private Integer dish_id;
    private String dish_name;
    private String description;
    private Integer price;
    private Integer onoff;

    public DishRequest(Integer rest_id, String dish_name, String description, Integer price, boolean onoff) {
        this.rest_id = rest_id;
        this.dish_name = dish_name;
        this.description = description;
        this.price = price;
        this.onoff = (onoff) ? 1 : 0;
    }

    public DishRequest(String type, Integer rest_id, Integer dish_id, String dish_name, String description, Integer price, boolean onoff) {
        this.type = type;
        this.rest_id = rest_id;
        this.dish_id = dish_id;
        this.dish_name = dish_name;
        this.description = description;
        this.price = price;
        this.onoff = (onoff) ? 1 : 0;
    }

    public DishRequest(String type, Integer dish_id) {
        this.type = type;
        this.dish_id = dish_id;
    }
}
