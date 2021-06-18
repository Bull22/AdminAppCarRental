package com.example.adminappcarrental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adminappcarrental.Utils.Common;
import com.example.adminappcarrental.ui.slideshow.SlideshowFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddNewCarActivity extends AppCompatActivity {
    private String nameCar;
    private String rateCar;
    private String carVersion;
    private String seat;
    private String gate;
    private String shift;
    private String carPrice;
    private boolean status;
    private String material;
    private String addressCar;


    private String categoryName;
    private String saveCurrentDate;
    private String saveCurrentTime;



    private EditText nameAddCar;
    private EditText rateAddCar;
    private EditText versionAddCar;
    private EditText seatAddCar;
    private EditText gateAddCar;
    private EditText shiftAddCar;
    private EditText priceAddCar;
    private EditText addressAddCar;
    private SwitchCompat statusAddCar;
    private EditText materialAddCar;
    private ImageView imgAddCar;
    private Button btnAddCar;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey;
    private String downloadImageUrl;
    private StorageReference productImagesRef;
    private DatabaseReference productRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);
        categoryName = getIntent().getExtras().get("category").toString();
        productImagesRef = FirebaseStorage.getInstance().getReference().child("Product Image");
        productRef = FirebaseDatabase.getInstance().getReference().child("products");

        nameAddCar = findViewById(R.id.tv_name_car_add);
        rateAddCar = findViewById(R.id.tv_rating_car_add);
        versionAddCar = findViewById(R.id.tv_version_car_add);
        seatAddCar = findViewById(R.id.tv_seat_car_add);
        gateAddCar = findViewById(R.id.tv_gate_car_add);
        shiftAddCar = findViewById(R.id.tv_shift_car_add);
        priceAddCar = findViewById(R.id.tv_price_car_add);
        statusAddCar = findViewById(R.id.tv_status_car_add);
        addressAddCar = findViewById(R.id.edt_address);
        materialAddCar = findViewById(R.id.tv_material_car_add);
        imgAddCar = findViewById(R.id.img_add_photo);
        btnAddCar = findViewById(R.id.btn_add_car);
        loadingBar = new ProgressDialog(this);

        imgAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        statusAddCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    status = true;
                } else {
                    status = false;
                }
//                HashMap<String, Object> dataMap = new HashMap<>();
//                dataMap.put("status", status);
//                FirebaseDatabase.getInstance().getReference()
//                .child("product").child("status")
//                .updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull @NotNull Task<Void> task) {
//
//                            }
//                        });

            }
        });
        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
    }


    private void openGallery() {
        Intent galleryInten = new Intent();
        galleryInten.setAction(Intent.ACTION_GET_CONTENT);
        galleryInten.setType("Image/*");
        startActivityForResult(galleryInten, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            imgAddCar.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData() {
        nameCar = nameAddCar.getText().toString();
        rateCar = rateAddCar.getText().toString();
        carVersion = versionAddCar.getText().toString();
        seat = seatAddCar.getText().toString();
        gate = gateAddCar.getText().toString();
        shift = shiftAddCar.getText().toString();
        carPrice = priceAddCar.getText().toString();
        addressCar = addressAddCar.getText().toString();

//        HashMap<String, Object> dataMap = new HashMap<>();
//        dataMap.put("status", status);
//        FirebaseDatabase.getInstance().getReference(Common.PRODUCT_INFO_CAR)
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .updateChildren(dataMap)
//                .addOnSuccessListener();
        material = materialAddCar.getText().toString();

        if (ImageUri == null) {
            Toast.makeText(this, "Bạn cần thêm ảnh..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(nameCar)) {
            Toast.makeText(this, "chưa có tên xe", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(rateCar)) {
            Toast.makeText(this, "chưa có đánh giá", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(carVersion)) {
            Toast.makeText(this, "chưa có phiên bản", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(seat)) {
            Toast.makeText(this, "chưa có số chỗ ngồi", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(gate)) {
            Toast.makeText(this, "chưa có số cửa", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(shift)) {
            Toast.makeText(this, "chưa có số xe", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(carPrice)) {
            Toast.makeText(this, "chưa có giá xe", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(material)) {
            Toast.makeText(this, "chưa có mô tả", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(addressCar)) {
            Toast.makeText(this, "chưa có địa chỉ", Toast.LENGTH_SHORT).show();
        } else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {

        loadingBar.setTitle("Thêm mới dữ liệu");
        loadingBar.setMessage("Hệ thống đang xử lý");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = productImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddNewCarActivity.this, "ERROR" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddNewCarActivity.this, "Product Image upload successful", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AddNewCarActivity.this, "got the product image URL successfully", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });




    }

    private void saveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("category", categoryName);
        productMap.put("image", downloadImageUrl);
        productMap.put("name", nameCar);
        productMap.put("rate", rateCar);
        productMap.put("version", carVersion);
        productMap.put("seat", seat);
        productMap.put("gate",gate);
        productMap.put("shift", shift);
        productMap.put("pice", carPrice);
        productMap.put("status", status);
        productMap.put("material", material);
        productMap.put("address", addressCar);

        productRef.child(productRandomKey).setValue(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(AddNewCarActivity.this, HomeAdminActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AddNewCarActivity.this, "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AddNewCarActivity.this, "ERROR" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}