package com.edentomer.med_track_final;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class RegistrationDoctorActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    public Uri ImageUri;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    EditText etEmail,etPassword,etName,etShort;
    Button RegisterBtn;
    boolean imagePicked = false;
    FirebaseStorage storage;
    private StorageReference storageReference;
    String profile_link;
    TextView candidate,employer,change;
    String who = "user";
    EditText etexp,etField;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    CardView signInButton;
    private int requestCode;
    private int resultCode;
    private Intent data;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_doctor);

        progressDialog=new ProgressDialog(RegistrationDoctorActivity.this);

        progressDialog.setMessage("Please Wait...");


        isReadStoragePermissionGranted();

        RegisterBtn = findViewById(R.id.RegisterBtn);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        etexp = findViewById(R.id.etexp);
        etField = findViewById(R.id.etField);

        etName = findViewById(R.id.etName);
        etShort = findViewById(R.id.etShort);
        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        change = findViewById(R.id.change);

        candidate = findViewById(R.id.candidate);
        employer = findViewById(R.id.employer);
        who = "user";

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(RegistrationDoctorActivity.this, LoginDoctorActivity.class);
                startActivity(intent);
            }
        });

        candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etexp.setVisibility(View.GONE);
                etField.setVisibility(View.GONE);

                if(who.equals("user")){

//                    etexp.setVisibility(View.GONE);
//                    etField.setVisibility(View.GONE);

                    candidate.setBackground(ContextCompat.getDrawable(RegistrationDoctorActivity.this, R.drawable.black_23));
                    candidate.setTextColor(Color.parseColor("#ffffff"));
                    employer.setBackground(ContextCompat.getDrawable(RegistrationDoctorActivity.this, R.drawable.back_outline));
                    employer.setTextColor(Color.parseColor("#000000"));


                }else{

//                    etexp.setVisibility(View.VISIBLE);
//                    etField.setVisibility(View.VISIBLE);
                    candidate.setBackground(ContextCompat.getDrawable(RegistrationDoctorActivity.this, R.drawable.black_23));
                    candidate.setTextColor(Color.parseColor("#ffffff"));
//                    candidate.setBackground(ContextCompat.getDrawable(RegistrationActivity.this, R.drawable.back_outline));
//                    candidate.setTextColor(Color.parseColor("#000000"));
                    employer.setBackground(ContextCompat.getDrawable(RegistrationDoctorActivity.this, R.drawable.back_outline));
                    employer.setTextColor(Color.parseColor("#000000"));

                }
                who = "user";


            }
        });
        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //candidate.setBackgroundResource(R.drawable.black_23);
                etexp.setVisibility(View.VISIBLE);
                etField.setVisibility(View.VISIBLE);
                if(who.equals("doctor")){
//                    etexp.setVisibility(View.VISIBLE);
//                    etField.setVisibility(View.VISIBLE);
                    candidate.setBackground(ContextCompat.getDrawable(RegistrationDoctorActivity.this, R.drawable.back_outline));
                    candidate.setTextColor(Color.parseColor("#000000"));

                    employer.setBackground(ContextCompat.getDrawable(RegistrationDoctorActivity.this, R.drawable.black_23));
                    employer.setTextColor(Color.parseColor("#ffffff"));

                }else{
//                    etexp.setVisibility(View.GONE);
//                    etField.setVisibility(View.GONE);
                    candidate.setBackground(ContextCompat.getDrawable(RegistrationDoctorActivity.this, R.drawable.back_outline));
                    candidate.setTextColor(Color.parseColor("#000000"));



                    employer.setBackground(ContextCompat.getDrawable(RegistrationDoctorActivity.this, R.drawable.black_23));
                    employer.setTextColor(Color.parseColor("#ffffff"));
//                    employer.setBackground(ContextCompat.getDrawable(RegistrationActivity.this, R.drawable.back_outline));
//                    employer.setTextColor(Color.parseColor("#000000"));

                }
                who = "doctor";
            }
        });
        
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signin();
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            progressDialog.show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        if (requestCode==1 && requestCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            ImageUri= data.getData();

            // pickImage.setImageURI(ImageUri);

            uploadPic();

        }
    }
    private void handleSignInResult(GoogleSignInResult result){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(result.getSignInAccount().getEmail(),"123456").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                String userID= FirebaseAuth.getInstance().getUid();
                databaseReference=firebaseDatabase.getReference("users").child(userID);


                String field = etField.getText().toString();
                String exp = etexp.getText().toString();
                databaseReference.child("image").setValue("default");
                databaseReference.child("thumb_image").setValue("default");
                databaseReference.child("online").setValue("true");
                databaseReference.child("device_token").setValue("true");
                databaseReference.child("about").setValue("Hi, I m using Aurora App");
                databaseReference.child("email").setValue(result.getSignInAccount().getEmail());
                databaseReference.child("name").setValue(result.getSignInAccount().getDisplayName());

                if(field.isEmpty()){
                    databaseReference.child("field").setValue("");

                }else{
                    databaseReference.child("field").setValue(field);

                }
                if(exp.isEmpty()){
                    databaseReference.child("exp").setValue("");

                }else{
                    databaseReference.child("exp").setValue(exp);

                }

                databaseReference.child("phone").setValue("unknown");
                databaseReference.child("role").setValue(who);

                databaseReference.child("status").setValue("Hi, I m using Aurora App");
                databaseReference.child("userId").setValue(userID).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();


                        Intent intent=new Intent(RegistrationDoctorActivity.this, MainDcotorActivity.class);
                        startActivity(intent);
                        finish();



                    }
                });

                Toast.makeText(RegistrationDoctorActivity.this, "Successfully Registered !", Toast.LENGTH_SHORT).show();




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();

                Toast.makeText(RegistrationDoctorActivity.this, "User Already Exist !", Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void gotoProfile(){
        Intent intent=new Intent(this,MainDcotorActivity.class);
        startActivity(intent);
    }

    public void signin() {

        if(etEmail.getText().toString().equals("")|| etPassword.getText().toString().equals("")|| etName.getText().toString().equals("") || etShort.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please Enter All Details", Toast.LENGTH_LONG).show();
            return;
        }

        else {

            registerUser();
        }

    }


    private void registerUser() {


        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString().trim(),etPassword.getText().toString().trim()).addOnCompleteListener(RegistrationDoctorActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {

                            progressDialog.dismiss();
                            Toast.makeText(RegistrationDoctorActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        } else {
                            addDataToFirebase(progressDialog);

                        }
                    }
                });


    }

    private void addDataToFirebase(ProgressDialog progressDialog)
    {
        String userID= FirebaseAuth.getInstance().getUid();
        databaseReference=firebaseDatabase.getReference("users").child(userID);


        String field = etField.getText().toString();
        String exp = etexp.getText().toString();
        databaseReference.child("image").setValue("default");
        databaseReference.child("thumb_image").setValue("default");
        databaseReference.child("online").setValue("true");
        databaseReference.child("device_token").setValue("true");
        databaseReference.child("about").setValue(etShort.getText().toString());
        databaseReference.child("email").setValue(etEmail.getText().toString());
        databaseReference.child("name").setValue(etName.getText().toString());

        if(field.isEmpty()){
            databaseReference.child("field").setValue("");

        }else{
            databaseReference.child("field").setValue(field);

        }
        if(exp.isEmpty()){
            databaseReference.child("exp").setValue("");

        }else{
            databaseReference.child("exp").setValue(exp);

        }

        databaseReference.child("phone").setValue("unknown");
        databaseReference.child("role").setValue(who);

        databaseReference.child("status").setValue(etShort.getText().toString());
        databaseReference.child("userId").setValue(userID).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();


                        Intent intent=new Intent(RegistrationDoctorActivity.this, MainDcotorActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });



            }
        });

        Toast.makeText(this, "Successfully Registered !", Toast.LENGTH_SHORT).show();



    }




    private void choosePic()
    {
        Intent intent;
        intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }


    private void uploadPic() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading picture...");
        pd.show();

        final String randomkey = UUID.randomUUID().toString();
        final StorageReference ref = storageReference.child("images/" + randomkey);
        ref.putFile(ImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                pd.dismiss();
                                imagePicked = true;
                                profile_link = downloadUrl.toString();
                                Toast.makeText(RegistrationDoctorActivity.this, "Picture uploaded !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(RegistrationDoctorActivity.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred()
                                / snapshot.getTotalByteCount());

                        pd.setMessage("Percentage: " + (int) progressPercent + "%");

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