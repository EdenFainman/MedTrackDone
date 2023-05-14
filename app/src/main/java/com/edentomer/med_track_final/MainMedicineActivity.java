package com.edentomer.med_track_final;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMedicineActivity extends AppCompatActivity implements View.OnClickListener  {

    FirebaseAuth auth;


    private CardView addItems, deleteItems, scanItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_main);



        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_outline_home_24, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("My Medicine", R.drawable.notification, R.color.colorAccent);
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

        bottomNavigation.setCurrentItem(2);

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

        //Firebase Auth
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // check if user is login or not
        if(user != null){

        }else{
            // if not then goto login screen
            finish();
            startActivity(new Intent(this, LoginDoctorActivity.class));
        }




        //init Views
        addItems = (CardView)findViewById(R.id.addItems);
        deleteItems = (CardView) findViewById(R.id.deleteItems);
        scanItems = (CardView) findViewById(R.id.scanItems);

        //add click listener
        addItems.setOnClickListener(this);
        deleteItems.setOnClickListener(this);
        scanItems.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            // on clicking buttons move to respective screen
            case R.id.addItems : i = new Intent(this, AddProduct2Activity.class); startActivity(i); break;
            case R.id.deleteItems : i = new Intent(this, DeleteProductsActivity.class);startActivity(i); break;
            case R.id.scanItems : i = new Intent(this, AllProductsActivity.class);startActivity(i); break;

            default: break;
        }
    }





    // logout
    private void Logout()
    {
        auth.signOut();
        finish();
        startActivity(new Intent(MainMedicineActivity.this, LoginDoctorActivity.class));
        Toast.makeText(MainMedicineActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }



}
