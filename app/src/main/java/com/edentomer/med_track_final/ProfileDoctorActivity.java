package com.edentomer.med_track_final;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ProfileDoctorActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    DatabaseReference database;
    FirebaseAuth auth;
    TextView tvname,tvabout;
    RoundedImageView image,editBtn;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_doctor);


        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_outline_home_24, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("My Medicines", R.drawable.notification, R.color.colorAccent);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Profile", R.drawable.ic_baseline_perm_identity_24, R.color.colorAccent);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Medicine", R.drawable.medicine, R.color.colorAccent);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item5);
        bottomNavigation.addItem(item4);

//
        bottomNavigation.setAccentColor(Color.parseColor("#1185E0"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setCurrentItem(3);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {

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

        //==========================================================


        CardView logout = findViewById(R.id.logout);


        tvname = findViewById(R.id.tvName);
        tvabout = findViewById(R.id.tvBio);
        image = findViewById(R.id.image);
        editBtn = findViewById(R.id.editBtn);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                overridePendingTransition(0, 0);

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginDoctorActivity.class));
                finish();



            }
        });

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();




        auth = FirebaseAuth.getInstance();

        database  = FirebaseDatabase.getInstance().getReference().child("users");

        if (auth.getCurrentUser() != null) {



            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {


                    if (snapshot.child(auth.getCurrentUser().getUid()).child("name").exists()) {

                        String role = snapshot.child(auth.getCurrentUser().getUid()).child("role").getValue().toString();
                        if(role.equals("user")){


                        }else  if(role.equals("doctor")){


                        }
                        String pic = snapshot.child(auth.getCurrentUser().getUid()).child("image").getValue().toString();
                        Picasso.with(getApplicationContext()).load(pic).placeholder(R.drawable.prfile_pic).error(R.drawable.prfile_pic).into(image);

                        if (!snapshot.child(auth.getCurrentUser().getUid()).child("name").getValue().toString().equalsIgnoreCase("your name")) {
                            tvname.setText(snapshot.child(auth.getCurrentUser().getUid()).child("name").getValue().toString());

                        }
                        if (!snapshot.hasChild("about")) {

                            tvabout.setText(snapshot.child(auth.getCurrentUser().getUid()).child("about").getValue().toString());

                        }


                    }

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
           // handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                   // handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void gotoMainActivity(){
        Intent intent=new Intent(this,RegistrationDoctorActivity.class);
        startActivity(intent);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}