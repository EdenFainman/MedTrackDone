package com.edentomer.med_track_final;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import com.edentomer.med_track_final.SendNotificationPack.APIService;
import com.edentomer.med_track_final.SendNotificationPack.Client;
import com.edentomer.med_track_final.SendNotificationPack.Data;
import com.edentomer.med_track_final.SendNotificationPack.MyResponse;
import com.edentomer.med_track_final.SendNotificationPack.NotificationSender;
import com.edentomer.med_track_final.SendNotificationPack.Token;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDcotorActivity extends AppCompatActivity {

    private RecyclerView mUsersList, spList,videoList;
    private DatabaseReference mUsersDatabaseReference;

    FirebaseRecyclerAdapter ff, Specialization,videos;
    RelativeLayout textDoctor;
    TextView mode;
    ImageView search;
    private APIService apiService;
    public List<Users> quotesModels = new ArrayList<>();
    ImageView doc;
    TextView special;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter noteAdapter;
    FloatingActionButton floating;
    TextView tvNo;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);


        isReadStoragePermissionGranted();
        doc = findViewById(R.id.doc);
        floating = findViewById(R.id.floating);
        tvNo = findViewById(R.id.tvNo);



        textDoctor = findViewById(R.id.textdoc);
        spList = (RecyclerView) findViewById(R.id.recyclerviewSp);
        mode = findViewById(R.id.mode);
        search = findViewById(R.id.search);
        special = findViewById(R.id.special);

        spList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));



        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_outline_home_24, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("My medicines", R.drawable.notification, R.color.colorAccent);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Profile", R.drawable.ic_baseline_perm_identity_24, R.color.colorAccent);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Medicine", R.drawable.medicine, R.color.colorAccent);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item5);
        bottomNavigation.addItem(item4);



        bottomNavigation.setAccentColor(Color.parseColor("#1185E0"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.enableItemAtPosition(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){

                    case 0:

                        startActivity(new Intent(getApplicationContext(), MainDcotorActivity.class));
                        overridePendingTransition(0, 0);

                        break;

                    case 1:
                        startActivity(new Intent(getApplicationContext(), RemindersActivity.class));
                        overridePendingTransition(0, 0);

                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), MainMedicineActivity.class));
                        overridePendingTransition(0, 0);

                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), ProfileDoctorActivity.class));
                        overridePendingTransition(0, 0);

                        break;
                }
                return true;
            }
        });


        if(FirebaseAuth.getInstance().getCurrentUser()==null){

            Intent profileIntent=new Intent(getApplicationContext(), LoginDoctorActivity.class);
            startActivity(profileIntent);
            finish();

        }else if(FirebaseAuth.getInstance().getCurrentUser()!=null){


        }


        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
            }
        });


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



            }
        });

        //===============
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupRecyclerView();
















        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){

            UpdateToken();

        }
    }

    public void setupRecyclerView(){

        //query

        String UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("appointments").child(UserId);
        com.google.firebase.database.Query firebaseSearchQuery = databaseReference;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.exists()){

                    //progress_bar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //firebase recyclerview
        FirebaseRecyclerOptions<ModelClass> options =
                new FirebaseRecyclerOptions.Builder<ModelClass>()
                        .setQuery(firebaseSearchQuery, ModelClass.class)
                        .build();

        noteAdapter = new FirebaseRecyclerAdapter<ModelClass, TodoViewHolder>(options) {
            @NonNull
            @Override
            public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                // attach layout to RecyclerView
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

                return new TodoViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(TodoViewHolder holder, int position, ModelClass model) {
                //progress_bar.setVisibility(View.GONE);

                tvNo.setVisibility(View.GONE);
                // set data in views
                holder.titleTextView.setText(model.getService());
                holder.timestampTextView.setText("Date: "+model.getDate());
                holder.tvTime.setText("Time: "+model.getTime());

                holder.btnEdit.setOnClickListener((v)->{
                    Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                    intent.putExtra("service",model.getService());
                    intent.putExtra("date",model.getDate());
                    intent.putExtra("time",model.getTime());

                    intent.putExtra("docId",model.getId());
                    startActivity(intent);

                });


                holder.btnCancel.setOnClickListener((v)->{


                    databaseReference.child(getRef(position).getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(MainDcotorActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });


                });

            }
        };


        noteAdapter.startListening();
        // add adapter to recyclerview

        recyclerView.setAdapter(noteAdapter);
    }
    //View Holder class
    public static class TodoViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView,timestampTextView,tvTime;
        Button btnCancel,btnEdit;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvTitle);
            timestampTextView = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);

            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnEdit = itemView.findViewById(R.id.btnEdit);

        }

    }

    private void UpdateToken(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        Token token= new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(ff!=null){
            ff.startListening();

        }


    }



    @RequiresApi(api = Build.VERSION_CODES.R)
    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                //Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.MANAGE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Log.v(TAG,"Permission is granted1");
            return true;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        isReadStoragePermissionGranted();

                    }

                } else {
                    // Permission Denied
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    }
                    //startLocationUpdates();
                } else {
                    // Permission Denied
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }


}