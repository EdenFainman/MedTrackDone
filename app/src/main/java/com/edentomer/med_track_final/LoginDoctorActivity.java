package com.edentomer.med_track_final;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginDoctorActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    EditText etEmail,etPassword;
    Button loginBtn;
    TextView candidate,employer,change;
    String who = "user";
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    CardView signInButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);


        progressDialog=new ProgressDialog(LoginDoctorActivity.this);

        progressDialog.setMessage("Please Wait...");



        loginBtn = findViewById(R.id.loginBtn);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        candidate = findViewById(R.id.candidate);
        employer = findViewById(R.id.employer);
        who = "user";
        change = findViewById(R.id.change);

        firebaseAuth = FirebaseAuth.getInstance();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(LoginDoctorActivity.this, RegistrationDoctorActivity.class);
                startActivity(intent);
            }
        });


        candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(who.equals("user")){
                    candidate.setBackground(ContextCompat.getDrawable(LoginDoctorActivity.this, R.drawable.black_23));
                    candidate.setTextColor(Color.parseColor("#ffffff"));
                    employer.setBackground(ContextCompat.getDrawable(LoginDoctorActivity.this, R.drawable.back_outline));
                    employer.setTextColor(Color.parseColor("#000000"));

                }else{

                    candidate.setBackground(ContextCompat.getDrawable(LoginDoctorActivity.this, R.drawable.black_23));
                    candidate.setTextColor(Color.parseColor("#ffffff"));
//                    candidate.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.back_outline));
//                    candidate.setTextColor(Color.parseColor("#000000"));
                    employer.setBackground(ContextCompat.getDrawable(LoginDoctorActivity.this, R.drawable.back_outline));
                    employer.setTextColor(Color.parseColor("#000000"));

                }
                who = "user";


            }
        });
        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //candidate.setBackgroundResource(R.drawable.black_23);

                if(who.equals("doctor")){

                    candidate.setBackground(ContextCompat.getDrawable(LoginDoctorActivity.this, R.drawable.back_outline));
                    candidate.setTextColor(Color.parseColor("#000000"));

                    employer.setBackground(ContextCompat.getDrawable(LoginDoctorActivity.this, R.drawable.black_23));
                    employer.setTextColor(Color.parseColor("#ffffff"));

                }else{

                    candidate.setBackground(ContextCompat.getDrawable(LoginDoctorActivity.this, R.drawable.back_outline));
                    candidate.setTextColor(Color.parseColor("#000000"));



                    employer.setBackground(ContextCompat.getDrawable(LoginDoctorActivity.this, R.drawable.black_23));
                    employer.setTextColor(Color.parseColor("#ffffff"));
//                    employer.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.back_outline));
//                    employer.setTextColor(Color.parseColor("#000000"));

                }
                who = "doctor";
            }
        });



//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseDatabase=FirebaseDatabase.getInstance();

        isReadStoragePermissionGranted();



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etEmail.getText().toString().equals("")|| etPassword.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Username and Password can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    loginUser();
                }
            }

        });


        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton=findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });
    }

    private void loginUser()
    {
        final ProgressDialog progressDialog=ProgressDialog.show(LoginDoctorActivity.this, "Please Wait...", "Processing...",true);

        firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString().trim(),etPassword.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            Toast.makeText(LoginDoctorActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            if(firebaseAuth.getCurrentUser()!=null){
                                if(firebaseAuth.getCurrentUser().isEmailVerified()){

                                    progressDialog.dismiss();
                                    Toast.makeText(LoginDoctorActivity.this, ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else{


                            }


                        } else {



                                progressDialog.dismiss();

                                Toast.makeText(LoginDoctorActivity.this, "Logged In Successfully !", Toast.LENGTH_SHORT).show();
                                LoginDoctorActivity.this.startActivity(new Intent(LoginDoctorActivity.this, MainDcotorActivity.class));
                                LoginDoctorActivity.this.finish();



                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            progressDialog.show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }


    }
    private void handleSignInResult(GoogleSignInResult result){

        FirebaseAuth.getInstance().signInWithEmailAndPassword(result.getSignInAccount().getEmail(),"123456").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {


                progressDialog.dismiss();


                Intent intent=new Intent(LoginDoctorActivity.this, MainDcotorActivity.class);
                startActivity(intent);
                finish();





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(LoginDoctorActivity.this, "User Already Exist !", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                //Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Log.v(TAG,"Permission is granted1");
            return true;
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

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