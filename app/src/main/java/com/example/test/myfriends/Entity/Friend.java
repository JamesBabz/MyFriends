package com.example.test.myfriends.Entity;

import android.location.Location;
import android.media.Image;

import java.io.Serializable;

/**
 * Created by thomas on 12-03-2018.
 */

public class Friend implements Serializable {

    private long id;
    private String name;
    private String address;
    private double Altitude;
    private double Longitude;
    private String phone;
    private String mail;
    private String website;
    private String birthday;
    private Image picture;

    public Friend(int id, String name, String address, Double Altitude, Double Longitude, String phone, String mail, String website, String birthday) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.Altitude = Altitude;
        this.Longitude = Longitude;
        this.phone = phone;
        this.mail = mail;
        this.website = website;
        this.birthday = birthday;
        this.picture = picture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAltitude() {
        return Altitude;
    }

    public void setAltitude(double altitude) {
        Altitude = altitude;
    }

    public double getLongitude() {

        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }
}
