package com.edentomer.med_track_final;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private String date,time;

    private String newDate;
    TextView tvDate,tvTime,selectTime,tvDes;
    boolean isEditMode = false;
    String timeStr,dateStr,docID,description;
    ProgressDialog progressDialog;
    int hour24hrs;
    String dayy;
    TextView page_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        Intent intent = getIntent();
        if (getIntent().hasExtra("date")) {
            date = intent.getStringExtra("date");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

            date = formatter.format(new Date());

        }



        if (getIntent().hasExtra("description")) {
            description = intent.getStringExtra("description");

        }

        if (getIntent().hasExtra("time")) {
            time = intent.getStringExtra("time");

        }
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        selectTime = findViewById(R.id.selectTime);
        page_title = findViewById(R.id.page_title);
        tvDes = findViewById(R.id.tvDes);
        newDate = date;

        //receive data
        dateStr = getIntent().getStringExtra("date");
        docID = getIntent().getStringExtra("docId");

        if (docID!=null && !docID.isEmpty()){
            isEditMode = true;
            tvTime.setText(time);
            selectTime.setText(time);
            tvDate.setText(dateStr);

        }

        if(isEditMode){
            page_title.setText("Edit Appointment");
        }



        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tvDate.setText(date);
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date2 = null;
        try {
            date2 = inFormat.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        dayy = outFormat.format(date2);


        Calendar calendar = Calendar.getInstance();
        hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);


        CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String dayString = String.valueOf(day);
                if (day < 10) {
                    dayString = "0" + dayString;
                }

                String monthString = String.valueOf(month + 1);
                if ((month + 1) < 10) {
                    monthString = "0" + monthString;
                }

                newDate = dayString + "-" + monthString + "-" + String.valueOf(year);
                tvDate.setText(newDate);


                SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date2 = null;
                try {
                    date2 = inFormat.parse(newDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                dayy = outFormat.format(date2);

            }
        });



        selectTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        selectTime.setText( selectedHour + ":" + selectedMinute);
                        tvTime.setText(selectedHour + ":" + selectedMinute);

                        hour24hrs = selectedHour;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



        Button bookAppointment = findViewById(R.id.bookAppointment);
        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!tvDate.getText().toString().isEmpty()){



                    Calendar calendar = Calendar.getInstance();
                    int hour24hrsCurrentTime = calendar.get(Calendar.HOUR_OF_DAY);


                    String customerDate = tvDate.getText().toString()+" "+tvTime.getText().toString()+":00";
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    try {
                        Date date = format.parse(customerDate);

                        long cusLong = date.getTime();

                        long currLong  = new Date().getTime();
                        saveNoteToFirebase(tvTime.getText().toString(),tvDate.getText().toString(),tvDes.getText().toString());

                        Log.e("ppppp", "onClick: "+cusLong+" "+ currLong);
                        System.out.println(date);



                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //if data is today and then hour must be greater than or equal to





                }


//
//



            }
        });

    }

    void saveNoteToFirebase(String time,String date,String service){
        DocumentReference documentReference;
        if (isEditMode){

            progressDialog.show();
            //update the note
//            documentReference = Utility.getCollectionReferenceForNotes().document(docID);


            FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("appointments").child(currentUser.getUid()).child(docID);
            databaseReference.child("service").setValue(service);

            databaseReference.child("date").setValue(date);

            databaseReference.child("time").setValue(time).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    progressDialog.dismiss();

                    Toast.makeText(CalendarActivity.this, "A appointment has been successfully scheduled", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainDcotorActivity.class);
                    startActivity(intent);
                    finish();

                }
            });



        }else{
            //create new note
//            documentReference = Utility.getCollectionReferenceForNotes().document();
            progressDialog.show();

            FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("appointments").child(currentUser.getUid()).child(time+date);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){
                        Toast.makeText(CalendarActivity.this, "Appointment already exist at that time", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else{
                        databaseReference.child("service").setValue(service);
                        databaseReference.child("date").setValue(date);
                        databaseReference.child("id").setValue(databaseReference.getKey());

                        databaseReference.child("time").setValue(time).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();

                                Toast.makeText(CalendarActivity.this, "A appointment has been successfully scheduled", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(),MainDcotorActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



        }



    }

}