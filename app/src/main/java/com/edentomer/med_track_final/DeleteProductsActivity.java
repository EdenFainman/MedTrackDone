package com.edentomer.med_track_final;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeleteProductsActivity extends AppCompatActivity {


    public static TextView resultdeleteview;
    private FirebaseAuth firebaseAuth;
    Button scantodelete, deletebtn;
    DatabaseReference databaseReference;
    AutoCompleteTextView etProductSearch;
    public ArrayList<Med> medModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        firebaseAuth = FirebaseAuth.getInstance();
        resultdeleteview = findViewById(R.id.barcodedelete);


        //Action bar to set text
        Med med1 = new Med("5702157142262","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/BRINTELLIX%20image.jpg?alt=media&token=d8012b69-31b2-4982-a51c-7bb7f140e022","Brintellix","10");
        Med med2 = new Med("7290000806419","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/ibufen%20image.jpg?alt=media&token=873f9b3b-22a3-4ed9-bb92-88438b995d05","Ibufen","10");
        Med med3 = new Med("7290000845494","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/Euthyrox%20image.jpg?alt=media&token=e72ed940-e7a8-4049-bfe7-0d5e9ccc76f0","Euthyrox","10");
        Med med4 = new Med("7290006813930","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/atozet%20image.jpg?alt=media&token=24254531-67d3-46d7-b329-de856a283af3","Atozet","10");
        Med med5 = new Med("7290008075817","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/LAMOGINE%20image.jpg?alt=media&token=db9d328f-0c79-4bd8-b2f6-a781a60225d9","Lamogine","10");
        Med med6 = new Med("7290008091138","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/MUCOLESS%20image.jpg?alt=media&token=b514eba0-b467-435e-99b8-8503b0d6f4e7","Mucoless","10");
        Med med7 = new Med("7290008102261","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/bactroban%20image.jpg?alt=media&token=2378ade4-71a5-447b-be96-86329b0cac47","Bactroban","10");
        Med med8 = new Med("7290013834089","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/forol%20image.jpg?alt=media&token=277fe30f-0fb8-4203-92ba-78de94a40e86","Forol","10");
        Med med9 = new Med("7290016845181","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/Nexium%20image.jpg?alt=media&token=b739e11f-88ea-4568-8f33-7b530f644b43","Nexium","10");
        Med med10 = new Med("7290104530685","https://firebasestorage.googleapis.com/v0/b/med-track-final.appspot.com/o/seroquel%20image.jpg?alt=media&token=07771230-403b-4cb0-bf34-e02faaf7228e","Seroquel","10");

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

        etProductSearch = (AutoCompleteTextView) findViewById(R.id.searchAutoComplete);
        ProductSearchDeleteAdapter adapter = new ProductSearchDeleteAdapter(this, android.R.layout.simple_dropdown_item_1line, medModels,resultdeleteview);
        etProductSearch.setAdapter(adapter );



        // database refernece
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // init Views
        scantodelete = findViewById(R.id.buttonscandelete);
        deletebtn= findViewById(R.id.deleteItemToTheDatabasebtn);


        // open qr/bar code scan activity
        scantodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivityDel.class));
            }
        });

        //delete button
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

    }

    public void delete()
    {
        // get product code
        String deletebarcodevalue = resultdeleteview.getText().toString();

        //get current user email
        final FirebaseUser users = firebaseAuth.getCurrentUser();

        // check code is not null
        if(!TextUtils.isEmpty(deletebarcodevalue)){

            //delete product/item
            databaseReference.child(users.getUid()).child("Items").child(deletebarcodevalue).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(DeleteProductsActivity.this,"Item is Deleted",Toast.LENGTH_SHORT).show();

                }
            });
        }
        else{
            Toast.makeText(DeleteProductsActivity.this,"Please scan Barcode",Toast.LENGTH_SHORT).show();
        }
    }
}
