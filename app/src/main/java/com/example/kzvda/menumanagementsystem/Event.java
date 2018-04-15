package com.example.kzvda.menumanagementsystem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Event {
    public int _id;
    public String _name;
    public String _description;
    public int _price;

    //drawable
    public String _bitmap;


    public Event() {
        super();
    }

    public Event(int id, String name, String description, int price, String bitmap) {
        this._id = id;
        this._name = name;
        this._description = description;
        this._price = price;
        this._bitmap = bitmap;
    }

    public Event(JSONObject object){
        try {
            this._id = object.getInt(DBHelper.KEY_ID);
            this._name = object.getString(DBHelper.KEY_NAME);
            this._description = object.getString(DBHelper.KEY_DESCRIPTION);
            this._price = object.getInt(DBHelper.KEY_PRICE);
            this._bitmap = object.getString(DBHelper.KEY_BITMAP);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Event.fromJson(jsonArray);
    public static ArrayList<Event> fromJson(JSONArray jsonObjects) {
        ArrayList<Event> users = new ArrayList<Event>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                users.add(new Event(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return users;
    }



    public int get_Id() {
        return _id;
    }

    public void set_Id(int _Id) {
        this._id = _Id;
    }


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_bitmap() {
        return _bitmap;
    }

    public void set_drawable(String _bitmap) {
        this._bitmap = _bitmap;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_price() {
        return _price;
    }

    public void set_price(int _price) {
        this._price = _price;
    }

}