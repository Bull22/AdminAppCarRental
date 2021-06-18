package com.example.adminappcarrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminappcarrental.Model.Admin;
import com.example.adminappcarrental.Utils.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.adminappcarrental.Model.Admin;


import org.jetbrains.annotations.NotNull;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "ERROR";
    private Button btnLogin;
    private EditText edtUser;
    private EditText edtPass;
    private TextView tvRegister;
    private ProgressDialog loadingBar;
    FirebaseAuth mAuth;
    DatabaseReference dataref;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        edtUser = findViewById(R.id.edt_user);
        edtPass = findViewById(R.id.edt_pass);
        tvRegister = findViewById(R.id.tv_register);
        btnLogin = findViewById(R.id.btn_login);
        loadingBar = new ProgressDialog(this);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Resignter.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = edtUser.getText().toString();
                String password = edtPass.getText().toString();
                GotoHome(mail, password);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String mail = bundle.getString("phone");
            String password = bundle.getString("pass");
            if (mail != null && password != null) {
                setFiels(mail, password);
            }
        }
        edtPass.setOnEditorActionListener(((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                String mail = edtUser.getText().toString().trim();
                String password = edtPass.getText().toString().trim();
                GotoHome(mail,password);
                return true;
            }
            return false;
        }

        ));
    }


    private void setFiels(String mail, String password) {
        edtUser.setText(mail);
        edtPass.setText(password);
    }

    private void GotoHome(String mail, String password) {
        loadingBar = new ProgressDialog(this);
        loadingBar.setCancelable(false);
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.setMessage("Please Wait logging in..");
        loadingBar.show();
        SharedPreferences sharedPreferences = getSharedPreferences("AutoLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!mail.isEmpty() && !password.isEmpty()) {

            editor.putString("phone", mail);
            editor.putString("Password", password);
            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    loadingBar.dismiss();
                    checkUserFromFirebase();

                }
            }).addOnFailureListener(e -> {
                loadingBar.dismiss();
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            });

        }
        editor.apply();
    }

    private void checkUserFromFirebase() {
        database = FirebaseDatabase.getInstance();
        dataref = database.getReference(Common.USER_INFO_REFERENCE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Log.d("snapshot", "onDataChange: " + snapshot);
                Admin admin = snapshot.getValue(Admin.class);
                Common.adminMode = admin;
                startActivity(new Intent(LoginActivity.this, HomeAdminActivity.class));
                finish();
                Toast.makeText(getApplicationContext(), "wellcome", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}


