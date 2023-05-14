package com.edentomer.med_track_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CheckStockHospitalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stock_hospital);

        ImageView im1 = findViewById(R.id.im1);
        ImageView im2 = findViewById(R.id.im2);
        ImageView im3 = findViewById(R.id.im3);

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://e-services.clalit.co.il/PharmacyStock/#/SearchMedication"));
                startActivity(browserIntent);
            }
        });

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://serguide.maccabi4u.co.il/heb/pharmacy/"));
                startActivity(browserIntent);
            }
        });

        im3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.meuhedet.co.il/%D7%9E%D7%90%D7%95%D7%97%D7%93%D7%AA-%D7%A4%D7%90%D7%A8%D7%9D/%D7%91%D7%93%D7%99%D7%A7%D7%AA-%D7%9E%D7%9C%D7%90%D7%99-%D7%AA%D7%A8%D7%95%D7%A4%D7%95%D7%AA-%D7%95%D7%A4%D7%A8%D7%99%D7%98%D7%99-%D7%91%D7%AA%D7%99-%D7%9E%D7%A8%D7%A7%D7%97%D7%AA-%D7%91%D7%9E%D7%90%D7%95%D7%97%D7%93%D7%AA-%D7%A4%D7%90%D7%A8%D7%9D/"));
                startActivity(browserIntent);
            }
        });
    }
}