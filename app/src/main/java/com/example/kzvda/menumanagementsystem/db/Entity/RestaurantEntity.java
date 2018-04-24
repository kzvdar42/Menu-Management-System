package com.example.kzvda.menumanagementsystem.db.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.kzvda.menumanagementsystem.db.Model.RestaurantModel;

@Entity()
public class RestaurantEntity implements RestaurantModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "subname")
    private String subname;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "website")
    private String website;

    @ColumnInfo(name = "photoSrc")
    private int photoSrc;

    @Ignore
    public RestaurantEntity(int id, String name, String subname, String description, String phoneNumber, String location, String website, int photoSrc) {
        this.id = id;
        this.name = name;
        this.subname = subname;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.website = website;
        this.photoSrc = photoSrc;
    }

    public RestaurantEntity(String name, String subname, String description, String phoneNumber, String location, String website, int photoSrc) {
        this.name = name;
        this.subname = subname;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.website = website;
        this.photoSrc = photoSrc;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public int getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(int photoSrc) {
        this.photoSrc = photoSrc;
    }
}
