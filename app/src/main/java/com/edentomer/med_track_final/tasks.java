package com.edentomer.med_track_final;

public class tasks {

    String name,img;


    public String getImg() {
        return img;
    }

    public tasks(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public tasks() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
