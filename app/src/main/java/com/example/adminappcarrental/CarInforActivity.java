package com.example.adminappcarrental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.adminappcarrental.Model.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class CarInforActivity extends AppCompatActivity {
    private TextView namecar;
    private TextView available;
    private ImageView imgStatus;
    private TextView rateCar;
    private TextView priceCar;
    private TextView shiftcar;
    private TextView seatCar;
    private TextView gasCar;
    private TextView describe;
    private ImageView imgCarInfo;
    private Button btnbackBooking;
    private Button btnDeleteCar;
    private String productID = "";
    private Context context;
    private Car car = new Car();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_infor);
        productID = getIntent().getStringExtra("pid");
        namecar = findViewById(R.id.infor_car);
        imgStatus = findViewById(R.id.imageView_notavailable);
        available = findViewById(R.id.notAvailableText);
        rateCar = findViewById(R.id.tv_rate_car_booking);
        priceCar = findViewById(R.id.tv_price_car);
        shiftcar = findViewById(R.id.tv_shift_car_booking);
        seatCar = findViewById(R.id.tv_seat_car_booking);
        describe = findViewById(R.id.tv_describe_car);
        imgCarInfo = findViewById(R.id.Image_car_booking);
        btnbackBooking = findViewById(R.id.btn_back_booking);
        btnDeleteCar = findViewById(R.id.btn_delete_car);
        getProductDetails(productID);
        btnbackBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarInforActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        btnDeleteCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference("products");
        dataRef.child(car.getPid()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                Intent intent = new Intent(CarInforActivity.this, HomeAdminActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
            }
        });


    }


    private void getProductDetails(String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products");
        if (productID != null) {
            productRef.child(productID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        car = snapshot.getValue(Car.class);
                        namecar.setText(car.getName());
                        // Glide.with(context)
                        //    .load(car.getImage())
                        //    .into(imgStatus);
//                        available.setText(car.isRenting());
                        if (car.getStatus() == true) {
                            available.setText("Xe Đang bận");
                            imgStatus.setImageResource(R.drawable.ic_x_icon);
                        } else {
                            available.setText("Sẵn sàng");
                            available.setTextColor(Color.parseColor("#7CFC00"));
                            imgStatus.setImageResource(R.drawable.ic_baseline_check_24);
                        }
                        rateCar.setText(car.getRate());
                        priceCar.setText(car.getPice());
                        shiftcar.setText(car.getShift());
                        seatCar.setText(car.getSeat());
                        describe.setText(car.getMaterial());
                        Glide.with(getApplicationContext())
                                .load(car.getImage())
                                .into(imgCarInfo);

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }
}