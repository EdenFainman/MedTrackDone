package com.edentomer.med_track_final;

public interface ReminderInterface {

    public void hideActionBar();
    public void showActionBar();
    public void addReminder(final String reminderTitle, final String reminderDTS, final long reminderTIM, String mon, String tue, String wed, String thr, String fri, String sat, String sun);
    public void updateReminder(final String reminderTitle, final String reminderDTS, final long reminderTIM, final int reminderId, final int reminderPosition, String mon, String tue, String wed, String thr, String fri, String sat, String sun);

}
