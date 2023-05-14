package com.edentomer.med_track_final;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllProductsActivity extends AppCompatActivity {
    public static EditText resultsearcheview;
    private FirebaseAuth firebaseAuth;
    ImageButton scantosearch;
    ImageView searchbtn;
    RecyclerView mrecyclerview;
    DatabaseReference mdatabaseReference;

    MedSearchAdapter adapter;
    public List<Med> medModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        //Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();


        //database reference
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(users.getUid()).child("Items");

        //init View
        resultsearcheview = findViewById(R.id.searchfield);
        scantosearch = findViewById(R.id.imageButtonsearch);
        searchbtn = findViewById(R.id.searchbtnn);
        mrecyclerview = findViewById(R.id.recyclerViews);
        //add layout manager to recyclerview
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mrecyclerview.setLayoutManager(manager);
        mrecyclerview.setHasFixedSize(true);
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));



        // open qr/bar code scanner screen
        scantosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivitySearch.class));
            }
        });




        mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dsp : snapshot.getChildren()){

                    Med med = new Med(dsp.child("itembarcode").getValue().toString(),
                            dsp.child("itemimage").getValue().toString(),
                            dsp.child("itemname").getValue().toString(),
                            dsp.child("itemstock").getValue().toString(),
                            dsp.child("itemdose").getValue().toString(),
                            dsp.child("itemleaf").getValue().toString());
                    ;

                    medModels.add(med);

                }
                adapter = new MedSearchAdapter(AllProductsActivity.this, medModels);
                mrecyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });








        resultsearcheview.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mrecyclerview.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(s.toString());
            }
        });


    }


}
