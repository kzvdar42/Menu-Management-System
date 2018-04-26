package com.example.kzvda.menumanagementsystem.serverApi.RequestBodies;

public class SetImageToDishRequest {
    private String type = "set_dish_photo";
    private int rest_id;
    private int dish_id;
    private String photo_src;

    public SetImageToDishRequest(int dish_id, String photo_src) {
        this.dish_id = dish_id;
        this.photo_src = photo_src;
    }

    public SetImageToDishRequest(String type, int rest_id, String photo_src) {
        this.type = type;
        this.rest_id = rest_id;
        this.photo_src = photo_src;
    }
}
