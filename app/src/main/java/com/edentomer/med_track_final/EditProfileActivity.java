package com.edentomer.med_track_final;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {



    EditText tvNameEdit,tvBioEdit;
    RoundedImageView imageEdit;
    LinearLayout edit;
    CardView rate,save;
    static  int RESULT_LOAD_IMG =42;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        auth =FirebaseAuth.getInstance();


        imageEdit = findViewById(R.id.imageEdit);
        tvBioEdit = findViewById(R.id.tvBioEdit);
        tvNameEdit = findViewById(R.id.tvNameEdit);

        edit =findViewById(R.id.edit);

        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
                //edit.setVisibility(View.GONE);

                //Save DATA

                String name = tvNameEdit.getText().toString();
                String bio = tvBioEdit.getText().toString();

                if(!name.isEmpty() && !bio.isEmpty() && auth.getCurrentUser()!=null){

                    DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                            if(!snapshot.hasChild(auth.getCurrentUser().getUid())){

                                Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_SHORT).show();



                            }else{


                                users.child(auth.getCurrentUser().getUid()).child("about").setValue(bio);
                                users.child(auth.getCurrentUser().getUid()).child("name").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }else{

                    onBackPressed();
                    //Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_SHORT).show();

                }







            }
        });

        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

            }
        });
        DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.hasChild(auth.getCurrentUser().getUid())){


                    if(!snapshot.child(auth.getCurrentUser().getUid()).child("name").getValue().toString().equalsIgnoreCase("your name")){
                        tvNameEdit.setText(snapshot.child(auth.getCurrentUser().getUid()).child("name").getValue().toString());;

                    }
                    if(!snapshot.child(auth.getCurrentUser().getUid()).child("about").getValue().toString().equalsIgnoreCase("Bio/ favorite quote.")){
                        tvBioEdit.setText(snapshot.child(auth.getCurrentUser().getUid()).child("about").getValue().toString());

                    }


                }else{


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //edit.setVisibility(View.GONE);

                //Save DATA
                onBackPressed();

//                String name = tvNameEdit.getText().toString();
//                String bio = tvBioEdit.getText().toString();
//
//                if(!name.isEmpty() && !bio.isEmpty() && auth.getCurrentUser()!=null){
//
//                    DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
//                    users.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//
//                            if(!snapshot.hasChild(auth.getCurrentUser().getUid())){
//
//                                Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_SHORT).show();
//
//
//
//                            }else{
//
//                                users.child(auth.getCurrentUser().getUid()).child("bio").setValue(bio);
//                                users.child(auth.getCurrentUser().getUid()).child("name").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//
//                                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
//
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                        }
//                    });
//                }else{
//
//                    Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_SHORT).show();
//
//                }






            }
        });
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);



        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageEdit.setImageBitmap(selectedImage);


                if(auth.getCurrentUser()!=null){

                    StorageReference riversRef = FirebaseStorage.getInstance().getReference().child(auth.getCurrentUser().getUid()).child("images/"+imageUri.getLastPathSegment());

                    UploadTask uploadTask = riversRef.putFile(imageUri);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            return riversRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                Uri downloadUri = task.getResult();

                                DatabaseReference dd = FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("image");
                                dd.setValue(downloadUri.toString());

                               // Toast.makeText(getApplicationContext(),"s "+downloadUri,Toast.LENGTH_SHORT).show();
                                //Log.e("toast",downloadUri.toString());
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });

                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(EditProfileActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(          EditProfileActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        isReadStoragePermissionGranted();

                    }
                    //startLocationUpdates();
                } else {
                    // Permission Denied
                }
                break;
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



    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Log.v(TAG,"Permission is granted2");\
                isReadStoragePermissionGranted();
                return true;
            } else {

                //Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Log.v(TAG,"Permission is granted2");
            return true;
        }
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
    public void onBackPressed() {

        String name = tvNameEdit.getText().toString();
        String bio = tvBioEdit.getText().toString();

        if(auth.getCurrentUser()!=null){

            if(!name.isEmpty() || !bio.isEmpty() ){

                DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        if(!snapshot.hasChild(auth.getCurrentUser().getUid())){

                            Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_SHORT).show();



                        }else{

                            if(!bio.isEmpty()){
                                users.child(auth.getCurrentUser().getUid()).child("about").setValue(bio).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                            if(!name.isEmpty() ){
                                users.child(auth.getCurrentUser().getUid()).child("name").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                                        //onBackPressed();
                                    }
                                });
                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }else{
        }


           // onBackPressed();
            //Toast.makeText(getApplicationContext(),"Please Login",Toast.LENGTH_SHORT).show();

        }


        super.onBackPressed();
    }
}