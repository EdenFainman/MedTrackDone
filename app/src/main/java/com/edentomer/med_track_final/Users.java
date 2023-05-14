package com.edentomer.med_track_final;


public class Users {
    public String name;
    public String status;
    public String image;
    public String thumb_image,device_token,email,online,phone,role,userId,about,exp,field;
    Users(){ }


    public String getThumb_image() {
        return thumb_image;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Users(String name, String status, String image, String thumb_image, String device_token, String email, String online, String phone, String role, String userId, String about, String exp, String field) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.thumb_image = thumb_image;
        this.device_token = device_token;
        this.email = email;
        this.online = online;
        this.phone = phone;
        this.role = role;
        this.userId = userId;
        this.about = about;
        this.exp = exp;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbImage() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
