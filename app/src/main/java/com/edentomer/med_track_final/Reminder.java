package com.edentomer.med_track_final;

public class Reminder {
    private int reminderId;
    private String reminderTitle;
    private String reminderDOF;
    private String reminderTOF;
    private long reminderTIM;

    String mon,tue,wed,thr,fri,sat,sun;

    public Reminder(int reminderId, String reminderTitle, String reminderDOF, String reminderTOF, long reminderTIM) {
        this.reminderId = reminderId;
        this.reminderTitle = reminderTitle;
        this.reminderDOF = reminderDOF;
        this.reminderTOF = reminderTOF;
        this.reminderTIM = reminderTIM;
    }

    public Reminder(int reminderId, String reminderTitle, String reminderDOF, String reminderTOF, long reminderTIM, String mon, String tue, String wed, String thr, String fri, String sat, String sun) {
        this.reminderId = reminderId;
        this.reminderTitle = reminderTitle;
        this.reminderDOF = reminderDOF;
        this.reminderTOF = reminderTOF;
        this.reminderTIM = reminderTIM;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thr = thr;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
    }


    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTue() {
        return tue;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThr() {
        return thr;
    }

    public void setThr(String thr) {
        this.thr = thr;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getReminderTitle() {
        return reminderTitle;
    }

    public void setReminderTitle(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }

    public String getReminderDOF() {
        return reminderDOF;
    }

    public void setReminderDOF(String reminderDOF) {
        this.reminderDOF = reminderDOF;
    }

    public String getReminderTOF() {
        return reminderTOF;
    }

    public void setReminderTOF(String reminderTOF) {
        this.reminderTOF = reminderTOF;
    }

    public long getReminderTIM() {
        return reminderTIM;
    }

    public void setReminderTIM(long reminderTIM) {
        this.reminderTIM = reminderTIM;
    }

}
