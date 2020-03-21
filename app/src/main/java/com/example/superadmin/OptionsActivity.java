package com.example.superadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OptionsActivity extends AppCompatActivity {

    private ImageView registerAdmin,registerSalesPerson,UpdateAdmin,UpdateSalesPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        registerAdmin=findViewById(R.id.registerAdmin);
        registerSalesPerson=findViewById(R.id.registerSalesPerson);
        UpdateAdmin=findViewById(R.id.updateAdmin);
        UpdateSalesPerson=findViewById(R.id.updateSalesman);


        registerAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsActivity.this, registerAdmin.class);
                startActivity(intent);
            }
        });

        registerSalesPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsActivity.this, registerSalesman.class);
                startActivity(intent);
            }
        });

        UpdateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsActivity.this, UpdateAdmin.class);
                startActivity(intent);
            }
        });

        UpdateSalesPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OptionsActivity.this, updateSalesperson.class);
                startActivity(intent);
            }
        });

    }
}
