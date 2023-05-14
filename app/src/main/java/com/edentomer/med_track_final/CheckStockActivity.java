package com.edentomer.med_track_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CheckStockActivity extends AppCompatActivity {

    Button btn_checkstock;
    ImageView im;
    TextView tvName,tvStock,tvDose,tvleaf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stock);

        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        String stock = getIntent().getStringExtra("stock");
        String dose = getIntent().getStringExtra("dose");
        String leaf = getIntent().getStringExtra("leaf");

        btn_checkstock = findViewById(R.id.btn_checkstock);
        im = findViewById(R.id.im);
        tvName = findViewById(R.id.tvName);
        tvStock = findViewById(R.id.tvStock);
        tvDose = findViewById(R.id.tvDose);
        tvleaf = findViewById(R.id.leaf);

        Picasso.with(getApplicationContext()).load(image).into(im);


        tvName.setText(name);
        tvStock.setText("Amount Left: "+stock);
        tvDose.setText("Dose "+dose);

        btn_checkstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),CheckStockHospitalActivity.class));
            }
        });

        tvleaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setDataAndType(Uri.parse(leaf), "application/pdf");
                startActivity(intent);
            }
        });
    }
}