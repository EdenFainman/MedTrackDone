package com.edentomer.med_track_final;

public class Request {

    String request_type,time,userIdClient,userIdTherapist,status,role;

    long timeLong;
    public Request() {
    }

    public Request(String request_type, String time, String userIdClient, String userIdTherapist, String status, long timeLong,String role) {
        this.request_type = request_type;
        this.time = time;
        this.userIdClient = userIdClient;
        this.userIdTherapist = userIdTherapist;
        this.status = status;
        this.timeLong = timeLong;
        this.role = role;

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(long timeLong) {
        this.timeLong = timeLong;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserIdClient() {
        return userIdClient;
    }

    public void setUserIdClient(String userIdClient) {
        this.userIdClient = userIdClient;
    }

    public String getUserIdTherapist() {
        return userIdTherapist;
    }

    public void setUserIdTherapist(String userIdTherapist) {
        this.userIdTherapist = userIdTherapist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
