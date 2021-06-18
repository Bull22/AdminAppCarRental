package com.example.adminappcarrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Resignter extends AppCompatActivity {
    private Button btnresignter;
    private EditText edtUserresignter;
    private EditText edtPhone;
    private EditText edtPassResignter;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resignter);
        edtUserresignter = findViewById(R.id.edt_user_resignter);
        edtPhone = findViewById(R.id.edt_phone_resignter);
        edtPassResignter = findViewById(R.id.edt_pass_resignter);
        btnresignter = findViewById(R.id.btn_resignter);
        loadingBar = new ProgressDialog(this);
        btnresignter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String name = edtUserresignter.getText().toString();
        String phone = edtPhone.getText().toString();
        String pass = edtPassResignter.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "chưa nhập tên...", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "chưa nhập sdt...", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "chưa nhập pass...", Toast.LENGTH_SHORT).show();
        } else  {
            loadingBar.setTitle("creat account");
            loadingBar.setMessage("wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Validate(name, phone, pass);
        }
    }

    private void Validate(String name, String phone, String pass) {
        final DatabaseReference dataRef;
        dataRef = FirebaseDatabase.getInstance().getReference();
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!(snapshot.child("Admin").child(phone).exists())){
                    HashMap<String, Object> admindataMap = new HashMap<>();
                    admindataMap.put("name", name);
                    admindataMap.put("phone", phone);
                    admindataMap.put("pass", pass);

                    dataRef.child("Admin").child(phone).updateChildren(admindataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                               if (task.isSuccessful()){
                                   Toast.makeText(Resignter.this, "Tạo thành công", Toast.LENGTH_SHORT).show();
                                   loadingBar.dismiss();

                                   Intent intent = new Intent(Resignter.this, LoginActivity.class);
                                   startActivity(intent);
                               }
                               else {
                                   loadingBar.dismiss();
                                   Toast.makeText(Resignter.this, "NETWORK ERROR!! thử lại", Toast.LENGTH_SHORT).show();
                               }
                                }
                            });
                } else {
                    Toast.makeText(Resignter.this, "this" + phone +"already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Resignter.this, "thuử tên khác", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Resignter.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}