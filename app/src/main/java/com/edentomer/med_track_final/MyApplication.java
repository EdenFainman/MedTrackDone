package com.edentomer.med_track_final;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class MyApplication extends Application{

    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate() {
        super.onCreate();

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();


        if(mUser!=null){

            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

            mAuth=FirebaseAuth.getInstance();


           mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
           mUserDatabase.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   if(dataSnapshot!=null){


                   }

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });




       }

    }



}
