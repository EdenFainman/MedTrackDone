package com.edentomer.med_track_final;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddProduct2Activity extends AppCompatActivity {


    public ArrayList<Med> medModels = new ArrayList<>();
    private EditText itembarcode,tvStock,dose;
    private FirebaseAuth firebaseAuth;
    public static TextView resulttextview;
    Button scanbutton, additemtodatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;

    ProgressDialog pd;
    Context context;
    FirebaseAuth auth;
    AutoCompleteTextView etProductSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product2);


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.searchAutoComplete);

        Med med1 = new Med("5702157142262","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/BRINTELLIX%20image.jpg?alt=media&token=d8012b69-31b2-4982-a51c-7bb7f140e022","Brintellix","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2Fbrintellix%20patient%20leaflet.pdf?alt=media&token=aac710b0-9cf8-43f6-af33-d5ce20a169ae");
        Med med2 = new Med("7290000806419","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/ibufen%20image.jpg?alt=media&token=873f9b3b-22a3-4ed9-bb92-88438b995d05","Ibufen","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2Fibufen%20patient%20leaflet.pdf?alt=media&token=d80a707b-c8f8-4a56-8334-94148ad5d4f1");
        Med med3 = new Med("7290000845494","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/Euthyrox%20image.jpg?alt=media&token=e72ed940-e7a8-4049-bfe7-0d5e9ccc76f0","Euthyrox","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2Feuthyrox%20patient%20leaflet.pdf?alt=media&token=7ee861c5-e7fa-46cb-b141-9b18455c01c0");
        Med med4 = new Med("7290006813930","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/atozet%20image.jpg?alt=media&token=24254531-67d3-46d7-b329-de856a283af3","Atozet","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2Fatozet%20patient%20leaflet.pdf?alt=media&token=6424f783-ecca-4411-99f5-5373d2615801");
        Med med5 = new Med("7290008075817","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/LAMOGINE%20image.jpg?alt=media&token=db9d328f-0c79-4bd8-b2f6-a781a60225d9","Lamogine","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2FLamogine%20patient%20leaflet.pdf?alt=media&token=587ca65c-0e0e-4b7f-a46e-dc8c9f1f0b55");
        Med med6 = new Med("7290008091138","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/MUCOLESS%20image.jpg?alt=media&token=b514eba0-b467-435e-99b8-8503b0d6f4e7","Mucoless","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2Fmucoless%20patient%20leaflet.pdf?alt=media&token=bab37ea9-38a6-403f-907e-21bcb42faea9");
        Med med7 = new Med("7290008102261","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/bactroban%20image.jpg?alt=media&token=2378ade4-71a5-447b-be96-86329b0cac47","Bactroban","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2Fbactroban%20patient%20leaflet.pdf?alt=media&token=1fa59009-9228-4aaf-a41f-9a78a566c57a");
        Med med8 = new Med("7290013834089","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/forol%20image.jpg?alt=media&token=277fe30f-0fb8-4203-92ba-78de94a40e86","Forol","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2Fforol%20patient%20leaflet.pdf?alt=media&token=62a7b92a-0414-4f69-87ce-6611b520a8c6");
        Med med9 = new Med("7290016845181","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/Nexium%20image.jpg?alt=media&token=b739e11f-88ea-4568-8f33-7b530f644b43","Nexium","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2FNEXIUM%20patient%20leaflet.pdf?alt=media&token=b1f16fe7-270c-47da-ba88-458499f75dfd");
        Med med10 = new Med("7290104530685","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/seroquel%20image.jpg?alt=media&token=07771230-403b-4cb0-bf34-e02faaf7228e","Seroquel","10","0","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/leafs%2FSeroquel%20patient%20leaflet.pdf?alt=media&token=4e62d59f-67a5-48ea-87ec-4eeb7af68841");

        medModels.add(med1);
        medModels.add(med2);
        medModels.add(med3);
        medModels.add(med4);
        medModels.add(med5);
        medModels.add(med6);
        medModels.add(med7);
        medModels.add(med8);
        medModels.add(med9);
        medModels.add(med10);

//initilaze this array with your data

        etProductSearch = (AutoCompleteTextView) findViewById(R.id.searchAutoComplete);
        ProductSearchAdapter adapter = new ProductSearchAdapter(this, android.R.layout.simple_dropdown_item_1line, medModels);
        etProductSearch.setAdapter(adapter );



        FirebaseApp.initializeApp(context);
        firebaseAuth = FirebaseAuth.getInstance();



        //Firebase authentication
        auth = FirebaseAuth.getInstance();
        //Progress bar
        pd = new ProgressDialog(this);
        pd.setTitle("Saving Data");
        pd.setMessage("Please Wait...");

        //Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        //init views
        resulttextview = findViewById(R.id.barcodeview);
        additemtodatabase = findViewById(R.id.additembuttontodatabase);
        scanbutton = findViewById(R.id.buttonscan);
        tvStock = findViewById(R.id.tvStock);
        dose = findViewById(R.id.dose);





        //Scan button to scan the code
        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });

        //add product to database
        additemtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(resulttextview.getText().toString().isEmpty() && !etProductSearch.getText().toString().isEmpty()){

                    addProduct();


                }else if(!resulttextview.getText().toString().isEmpty() && etProductSearch.getText().toString().isEmpty()){

                    addProductBarCode();
                }else{
                    Toast.makeText(context, "Please Enter something", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    public  void addProductBarCode(){

        //getring values from fields

        for(Med d : medModels){


            if(d.getItembarcode() != null && d.getItembarcode().equals(resulttextview.getText().toString()) && !tvStock.getText().toString().isEmpty() && !dose.getText().toString().isEmpty()){


                final FirebaseUser users = firebaseAuth.getCurrentUser();
                // all fields must be filled

                //upload product to database

                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itembarcode").setValue(d.getItembarcode());
                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemimage").setValue(d.getItemimage());
                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemname").setValue(d.getItemname());

                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemstock").setValue(tvStock.getText().toString());
                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemdose").setValue(dose.getText().toString());
                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemleaf").setValue(d.getItemleaf());



                resulttextview.setText("");
                etProductSearch.setText("");


                Toast.makeText(AddProduct2Activity.this," Added",Toast.LENGTH_SHORT).show();



            };

            //something here
        }



    }

    public  void addProduct(){

        //getring values from fields

        for(Med d : medModels){
            if(d.getItemname() != null && d.getItemname().contains(etProductSearch.getText().toString())){


                final FirebaseUser users = firebaseAuth.getCurrentUser();
                // all fields must be filled

                //upload product to database
                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itembarcode").setValue(d.getItembarcode());
                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemimage").setValue(d.getItemimage());
                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemname").setValue(d.getItemname());

                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemstock").setValue(tvStock.getText().toString());
                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemdose").setValue(dose.getText().toString());
                databaseReference.child(users.getUid()).child("Items").child(d.getItembarcode()).child("itemleaf").setValue(d.getItemleaf());

                resulttextview.setText("");
                etProductSearch.setText("");


                Toast.makeText(AddProduct2Activity.this," Added",Toast.LENGTH_SHORT).show();



            };
            //something here
        }



    }

}