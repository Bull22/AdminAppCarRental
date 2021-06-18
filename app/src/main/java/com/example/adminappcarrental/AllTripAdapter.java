package com.example.adminappcarrental;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminappcarrental.Model.Trip;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllTripAdapter extends RecyclerView.Adapter<AllTripAdapter.ViewHolder> {
    private DatabaseReference mUser, mCar;
    private Context context;
    private ArrayList<Trip> mTrip = new ArrayList<>();
    private String statusTrip = "";

    public AllTripAdapter(Context context, ArrayList<Trip> mTrip) {
        this.context = context;
        this.mTrip = mTrip;
    }

    @NonNull
    @NotNull
    @Override
    public AllTripAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_trip, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllTripAdapter.ViewHolder holder, int position) {
        Trip trip = mTrip.get(position);
        statusTrip = trip.getStatusTrip();

        mCar = FirebaseDatabase.getInstance().getReference().child("products");
        mUser = FirebaseDatabase.getInstance().getReference().child("User");
        String idCar = trip.getCarIdTrip();
        String idUser = trip.getUid();
        String idTrip = trip.getTripId();
        mCar.child(idCar).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String img = snapshot.child("image").getValue(String.class);
                String nameCar = snapshot.child("name").getValue(String.class);
                holder.tvNameCarTrip.setText(nameCar);
                Glide.with(context).load(img).into(holder.imgCarTrip);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        mUser.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String nameuser = snapshot.child("email").getValue(String.class);
                holder.tvNameUserTrip.setText(nameuser);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        holder.tvPriceCarTrip.setText(trip.getPriceCartrip() + " VNĐ");
        holder.tvStartDateTrip.setText(trip.getStartDateTrip() + " - " + trip.getEndDateTrip());
        if (trip.getHasDriverTrip() == true) {
            holder.tvDriverTrip.setText("Có tài xế");
            holder.tvDriverTrip.setTextColor(Color.parseColor("#00ff00"));
        } else {
            holder.tvDriverTrip.setText("Không có tài xế");
            holder.tvDriverTrip.setTextColor(Color.parseColor("#ff0000"));
        }


//        if (statusTrip.equals("Đang chờ")) {
//
//        } else if (trip.getStatusTrip().equals("Đang chạy")) {
//            holder.tvStatusTrip.setTextColor(Color.parseColor("#00ff00"));
//            holder.tvStatusTrip.setText("Đang chạy");
//            holder.tvButton.setText("KẾT THÚC");
//            holder.btn_ActionTrip.setVisibility(View.VISIBLE);
//            holder.btn_ActionTrip.setBackgroundColor(Color.parseColor("#ff0000"));
//        } else if (trip.getStatusTrip().equals("Đã kết thúc")) {
//            holder.btn_ActionTrip.setVisibility(View.GONE);
//            holder.tvStatusTrip.setText("Đã kết thúc");
//        }
        if (statusTrip.equals("Đang chờ")) {
            holder.btn_ActionTrip.setVisibility(View.VISIBLE);
            holder.tvStatusTrip.setTextColor(Color.parseColor("#ff7f50"));
            holder.tvStatusTrip.setText("Đang chờ");
            holder.tvButton.setText("XUẤT PHÁT");

            holder.btn_ActionTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (statusTrip.equals("Đang chờ")) {
                        Map<String, Object> updateStatus = new HashMap<>();
                        updateStatus.put("statusTrip", "Đang chạy");


                        FirebaseDatabase.getInstance().getReference("AllTrip")
                                .child(idUser)
                                .child(idTrip)
                                .updateChildren(updateStatus)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "complete", Toast.LENGTH_SHORT).show();
                                       Map<String, Object> updateStatusCar = new HashMap<>();
                                       updateStatusCar.put("status", true);

                                       FirebaseDatabase.getInstance().getReference("products")
                                               .child(idCar)
                                               .updateChildren(updateStatusCar)
                                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                   @Override
                                                   public void onSuccess(Void unused) {

                                                   }
                                               }).addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull @NotNull Exception e) {

                                           }
                                       });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {

                                    }
                                });

                    }

                }

            });
        } else if (trip.getStatusTrip().equals("Đang chạy")) {
            holder.tvStatusTrip.setTextColor(Color.parseColor("#00ff00"));
            holder.tvStatusTrip.setText("Đang chạy");
            holder.tvButton.setText("KẾT THÚC");
            holder.tvButton.setBackgroundColor(Color.parseColor("#ff0000"));
            holder.btn_ActionTrip.setBackgroundColor(Color.parseColor("#ff0000"));

            holder.btn_ActionTrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> updateStatus = new HashMap<>();
                    updateStatus.put("statusTrip", "Đã kết thúc");


                    FirebaseDatabase.getInstance().getReference("AllTrip")
                            .child(idUser)
                            .child(idTrip)
                            .updateChildren(updateStatus)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "complete", Toast.LENGTH_SHORT).show();
//                                    holder.btn_ActionTrip.setVisibility(View.GONE);
//                                    holder.tvStatusTrip.setText("Đã kết thúc");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {

                                }
                            });

                }
            });
        }
        else if (trip.getStatusTrip().equals("Đã kết thúc")){
            holder.btn_ActionTrip.setVisibility(View.GONE);
            holder.tvStatusTrip.setText("Đã kết thúc");

        }


    }

    @Override
    public int getItemCount() {
        return mTrip.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameCarTrip, tvPriceCarTrip, tvStartDateTrip, tvDriverTrip, tvStatusTrip, tvNameUserTrip, tvButton;
        public ImageView imgCarTrip;
        public LinearLayout btn_ActionTrip;
        public View mView;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mView = itemView;

            btn_ActionTrip = mView.findViewById(R.id.linear);
            tvPriceCarTrip = mView.findViewById(R.id.tv_price_car_trip);
            tvStartDateTrip = mView.findViewById(R.id.tv_start_date_trip);
            tvDriverTrip = mView.findViewById(R.id.tv_driver_trip);
            tvStatusTrip = mView.findViewById(R.id.tv_status_trip);
            tvNameUserTrip = mView.findViewById(R.id.tv_name_user_trip);
            tvNameCarTrip = mView.findViewById(R.id.tv_name_car_trip);
            imgCarTrip = mView.findViewById(R.id.img_car_trip);
            tvButton = mView.findViewById(R.id.tv_cancel_trip);
        }
    }
}

