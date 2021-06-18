package com.example.adminappcarrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adminappcarrental.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private Button btbBackuser;
    FirebaseAuth mAuth;
    private RecyclerView recyclerViewUser;
    private DatabaseReference mUser;
    private UserAdapter userAdapter;
    public View view;
    public static ArrayList<User> ListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        recyclerViewUser = findViewById(R.id.recycle_user);
        btbBackuser = findViewById(R.id.btn_back_user);
        ListUser = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseDatabase.getInstance().getReference("User");
        mUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()){
                        User user = childSnapshot.getValue(User.class);
                        ListUser.add(user);

                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerViewUser.setLayoutManager(linearLayoutManager);
                userAdapter = new UserAdapter(getApplicationContext(), ListUser);
                recyclerViewUser.setAdapter(userAdapter);
                recyclerViewUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btbBackuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(UserActivity.this, HomeAdminActivity.class ));
            }
        });
    }
}