package com.edentomer.med_track_final;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ERIC on 09/07/2020.
 */

public class ReminderFSD extends DialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    protected LinearLayout nRDL;
    protected TextView nRDateTvw;
    protected LinearLayout nRTL;
    protected TextView nRTimeTvw;
    protected TextInputEditText nRTitleTIEditT;
    protected TextInputLayout nRTitleTILayout;
    protected ImageButton actionBarCloseButton;
    protected TextView titleTvw;

    private int setDay = 0;
    private int setMonth = 0;
    private int setYear = 0;
    private int setHour = 0;
    private int setMinute = 0;
    protected Calendar setCalendar;

    protected DatePickerDialog nRDatePDialog;
    protected TimePickerDialog nRTimePDialog;

    protected ReminderInterface reminderInterface;

    protected int reminderId;
    protected String reminderTitle;
    protected String reminderDOF;
    protected String reminderTOF;
    protected long reminderTIM;
    protected int reminderPosition;
    protected boolean isEditMode;
    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7;

    String mon="false";
            String tue="false";String wed="false";String thr="false";String fri="false";String sat="false";String sun="false";


    protected static ReminderFSD newInstance(int reminderId, String reminderTitle, String reminderDOF, String reminderTOF, long reminderTIM, int reminderPosition, boolean isEditMode, String mon, String tue, String wed, String thr, String fri, String sat, String sun) {
        ReminderFSD newReminderFSD = new ReminderFSD();
        Bundle params = new Bundle();

        params.putInt("reminderId", reminderId);
        params.putString("reminderTitle", reminderTitle);
        params.putString("reminderDOF", reminderDOF);
        params.putString("reminderTOF", reminderTOF);
        params.putLong("reminderTIM", reminderTIM);
        params.putInt("reminderPosition", reminderPosition);
        params.putBoolean("isEditMode", isEditMode);

        params.putString("mon", mon);
        params.putString("tue", tue);
        params.putString("wed", wed);
        params.putString("thr", thr);
        params.putString("fri", fri);
        params.putString("sat", sat);
        params.putString("sun", sun);


        newReminderFSD.setArguments(params);
        return newReminderFSD;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reminderId = getArguments().getInt("reminderId");
        reminderTitle = getArguments().getString("reminderTitle");
        reminderDOF = getArguments().getString("reminderDOF");
        reminderTOF = getArguments().getString("reminderTOF");
        reminderTIM = getArguments().getLong("reminderTIM");
        reminderPosition = getArguments().getInt("reminderPosition");
        isEditMode = getArguments().getBoolean("isEditMode");

        mon = getArguments().getString("mon");
        tue = getArguments().getString("tue");
        wed = getArguments().getString("wed");
        thr = getArguments().getString("thr");
        fri = getArguments().getString("fri");
        sat = getArguments().getString("sat");
        sun = getArguments().getString("sun");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_rfsd, container, false);

        actionBarCloseButton = rootView.findViewById(R.id.action_bar_close_button);
        actionBarCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(rootView);
                dismiss();
            }
        });

        nRDL = rootView.findViewById(R.id.new_reminder_date_layout);
        nRDateTvw =rootView.findViewById(R.id.nrdl_date_value_tvw);
        nRTL = rootView.findViewById(R.id.new_reminder_time_layout);
        nRTimeTvw= rootView.findViewById(R.id.nrtl_time_value_tvw);

        titleTvw = rootView.findViewById(R.id.action_bar_title_tvw);

        cb1 = rootView.findViewById(R.id.cb1);
        cb2 = rootView.findViewById(R.id.cb2);
        cb3 = rootView.findViewById(R.id.cb3);
        cb4 = rootView.findViewById(R.id.cb4);
        cb5 = rootView.findViewById(R.id.cb5);
        cb6 = rootView.findViewById(R.id.cb6);
        cb7 = rootView.findViewById(R.id.cb7);



        if(isEditMode){
            titleTvw.setText("Edit Reminder");
        }

        else {
            titleTvw.setText("New Reminder");
        }

        reminderInterface = (ReminderInterface)getActivity();

        String nowDTS = getDTS(getNowTIM());
        String nowDS = nowDTS.substring(7,17);
        String nowTS = nowDTS.substring(0, 5);

        nRDateTvw.setText(nowDS);
        nRTimeTvw.setText(nowTS);

        setCalendar = Calendar.getInstance();

        nRTitleTILayout = rootView.findViewById(R.id.nr_title_til);
        nRTitleTIEditT = rootView.findViewById(R.id.nr_title_tiet);

        Button actionBarSButton = rootView.findViewById(R.id.action_bar_save_button);
        actionBarSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nReminderTitle = nRTitleTIEditT.getText().toString();
                long nReminderTIM = setCalendar.getTimeInMillis();
                String nReminderDTS = getDTS(nReminderTIM);


                if(cb1.isChecked()){
                    mon = "true";
                }
                if(cb2.isChecked()){
                    tue = "true";
                }
                if(cb3.isChecked()){
                    wed = "true";
                }
                if(cb4.isChecked()){
                    thr = "true";
                }
                if(cb5.isChecked()){
                    fri = "true";
                }
                if(cb6.isChecked()){
                    sat = "true";
                }
                if(cb7.isChecked()){
                    sun = "true";
                }

                if(!dialogTextIsSpaces(nReminderTitle)){

                    if(!isEditMode){
                        reminderInterface.addReminder(nReminderTitle,nReminderDTS,nReminderTIM,mon,tue,wed,thr,fri,sat,sun);
                    }
                    else {
                        reminderInterface.updateReminder(nReminderTitle,nReminderDTS,nReminderTIM,reminderId,reminderPosition,mon,tue,wed,thr,fri,sat,sun);
                    }

                    hideKeyboard(rootView);
                    dismiss();
                }

                else {
                    nRTitleTILayout.setErrorEnabled(true);
                    nRTitleTILayout.setError(getResources().getString(R.string.teit_error_text));

                    nRTitleTIEditT.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            String editTextText = nRTitleTIEditT.getText().toString();
                            boolean textIsSpaces = dialogTextIsSpaces(editTextText);

                            if(textIsSpaces){
                                nRTitleTILayout.setErrorEnabled(true);
                                nRTitleTILayout.setError(getResources().getString(R.string.teit_error_text));
                            }
                            else {
                                nRTitleTILayout.setErrorEnabled(false);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });
                }
            }
        });

        initializeDateAndTimePickerDialogs();

        nRDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nRDatePDialog.show();
            }
        });

        nRTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nRTimePDialog.show();
            }
        });

        if(isEditMode){
            setEditMode();
        }

        if(mon.equals("true")){
            cb1.setChecked(true);
        }
        if(tue.equals("true")){
            cb2.setChecked(true);
        }
        if(wed.equals("true")){
            cb3.setChecked(true);
        }
        if(thr.equals("true")){
            cb4.setChecked(true);
        }
        if(fri.equals("true")){
            cb5.setChecked(true);
        }
        if(sat.equals("true")){
            cb6.setChecked(true);
        }
        if(sun.equals("true")){
            cb7.setChecked(true);
        }


        reminderInterface.hideActionBar();

        return rootView;
    }

    protected boolean dialogTextIsSpaces(String dialogText){
        int i = 0;
        int noOfSpaces = 0;
        int dialogTextLength = dialogText.length();

        while (i >= 0 && i <= (dialogTextLength-1)){
            String dialogTextCharacter= String.valueOf(dialogText.charAt(i));

            if(dialogTextCharacter.equals(" ")){
                noOfSpaces = noOfSpaces+1;
            }

            ++i;
        }

        if(dialogTextLength == noOfSpaces){
            return true;
        }

        else {
            return false;
        }

    }

    private void setEditMode(){
        nRTitleTIEditT.setText(String.valueOf(reminderTitle));
        nRDateTvw.setText(reminderDOF);
        nRTimeTvw.setText(reminderTOF);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        reminderInterface.showActionBar();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog reminderFSD = super.onCreateDialog(savedInstanceState);
        reminderFSD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return reminderFSD;
    }

    private long getNowTIM(){
        Date nowDate =new Date();
        long nowTIM = nowDate.getTime();
        return nowTIM;
    }

    private String getDTS(long timeInMills){
        Date date= new Date(timeInMills);
        DateFormat dateFormat=new SimpleDateFormat("HH:mm  dd/MM/yyyy");
        String dateTimeString=  dateFormat.format(date);
        return dateTimeString;
    }

    private void initializeDateAndTimePickerDialogs(){
        if(isEditMode){
            setCalendar.setTimeInMillis(reminderTIM);
        }
        else {
            setCalendar.setTimeInMillis(getNowTIM());
        }

        int nCYear=setCalendar.get(Calendar.YEAR);
        int nCMonth= setCalendar.get(Calendar.MONTH);
        int nCDay=setCalendar.get(Calendar.DAY_OF_MONTH);
        int nCHour= setCalendar.get(Calendar.HOUR_OF_DAY);
        int nCMinute = setCalendar.get(Calendar.MINUTE);

        setYear = nCYear;
        setMonth = nCMonth;
        setDay = nCDay;
        setHour = nCHour;
        setMinute = nCMinute;

        nRDatePDialog=new DatePickerDialog(getContext(), this, nCYear, nCMonth, nCDay);
        nRTimePDialog=new TimePickerDialog(getContext(), this, nCHour,  nCMinute, true);

        nRDatePDialog.setCanceledOnTouchOutside(true);
        nRTimePDialog.setCanceledOnTouchOutside(true);

        nRDatePDialog.setTitle("Set Reminder Date");
        nRTimePDialog.setTitle("Set Reminder Time");
    }

    private void hideKeyboard(View view){
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void updateNRDTvw() {
        setCalendar.set(setYear,setMonth,setDay,setHour,setMinute);
        String dateTimeString = getDTS(setCalendar.getTimeInMillis());
        nRDateTvw.setText(dateTimeString.substring(7,17));
    }

    private void updateNRTTvw() {
        setCalendar.set(setYear,setMonth,setDay,setHour,setMinute);
        String dateTimeString = getDTS(setCalendar.getTimeInMillis());
        nRTimeTvw.setText(dateTimeString.substring(0,5));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        setYear =i;
        setMonth = i1;
        setDay =i2;
        updateNRDTvw();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        setHour =i;
        setMinute =i1;
        updateNRTTvw();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
