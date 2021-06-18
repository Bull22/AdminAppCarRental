package com.example.adminappcarrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddCarActivity extends AppCompatActivity {
    private ImageView imgMiniCar;
    private ImageView imgBus;
    private ImageView imgPickUpTruck;
    private ImageView imgTruck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        imgMiniCar = findViewById(R.id.img_car_mini);
        imgBus = findViewById(R.id.img_bus);
        imgPickUpTruck = findViewById(R.id.img_pick_up_truck);
        imgTruck = findViewById(R.id.img_truck);

        imgMiniCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCarActivity.this, AddNewCarActivity.class);
                intent.putExtra("category", "xe con");
                startActivity(intent);
            }
        });
        imgBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCarActivity.this, AddNewCarActivity.class);
                intent.putExtra("category", "xe khách");
                startActivity(intent);
            }
        });
        imgPickUpTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCarActivity.this, AddNewCarActivity.class);
                intent.putExtra("category", "xe bán tải");
                startActivity(intent);
            }
        });
        imgTruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCarActivity.this, AddNewCarActivity.class);
                intent.putExtra("category", "xe tải");
                startActivity(intent);
            }
        });
    }
}