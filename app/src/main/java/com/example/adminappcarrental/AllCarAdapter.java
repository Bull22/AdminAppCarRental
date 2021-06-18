package com.example.adminappcarrental;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminappcarrental.Interface.ItemClickListener;

import org.jetbrains.annotations.NotNull;

public class AllCarAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtNameCar, txtRateCar, txtVersion, txtSeat, txtGate, txtShift, txtPriceCar, txtMaterial;
    public ImageView imgCar;
    public ItemClickListener itemClickListner;
    public AllCarAdapter(@NonNull @NotNull View itemView) {
        super(itemView);
        imgCar = itemView.findViewById(R.id.img_car);
        txtNameCar = itemView.findViewById(R.id.tv_name_car);
        txtRateCar = itemView.findViewById(R.id.tv_rate_car);
        txtVersion = itemView.findViewById(R.id.tv_status);
        txtSeat = itemView.findViewById(R.id.seat_car);
        txtGate = itemView.findViewById(R.id.gate_car);
        txtShift = itemView.findViewById(R.id.shift);
        txtPriceCar = itemView.findViewById(R.id.price_car);
    }
    public void setItemClickListner(ItemClickListener listner){this.itemClickListner = listner;}

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(), false);

    }
}
