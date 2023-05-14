package com.edentomer.med_track_final;

public class ModelClass {
    String date;
    String price;
    String service;
    String time;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModelClass() {
    }


    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public ModelClass(String date, String price, String service, String time) {
        this.date = date;
        this.price = price;
        this.service = service;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
