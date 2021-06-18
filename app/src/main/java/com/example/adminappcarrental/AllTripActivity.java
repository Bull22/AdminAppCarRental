package com.example.adminappcarrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.adminappcarrental.Model.Trip;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AllTripActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button backTrip;
    private RecyclerView recyclerView;
    private AllTripAdapter allTripAdapter;
    private DatabaseReference mAllTrip, mUser, mCar;
    public View view;
    FirebaseDatabase db;
    public static ArrayList<Trip> listAllTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trip);
        backTrip = findViewById(R.id.btn_back_trip);


        recyclerView = findViewById(R.id.recycle_all_trip);
        listAllTrip = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        mAllTrip = FirebaseDatabase.getInstance()
                .getReference("AllTrip");
        mAllTrip.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    listAllTrip.clear();
                    for (DataSnapshot tripSnapshot : childSnapshot.getChildren()) {
                        Trip trip = tripSnapshot.getValue(Trip.class);
                        listAllTrip.add(trip);

                    }
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                allTripAdapter = new AllTripAdapter(getApplicationContext(), listAllTrip);
                recyclerView.setAdapter(allTripAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        backTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllTripActivity.this, HomeAdminActivity.class));
                finish();
            }
        });


    }


}