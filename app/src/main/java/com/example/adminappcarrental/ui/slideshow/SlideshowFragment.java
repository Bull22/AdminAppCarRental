package com.example.adminappcarrental.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.adminappcarrental.AddNewCarActivity;
import com.example.adminappcarrental.R;
import com.example.adminappcarrental.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.tvAddCar;
        final ImageView imgMiniCar = binding.imgCarMini;
        final ImageView imgBus = binding.imgBus;
        final ImageView imgPickUpTruck = binding.imgPickUpTruck;
        final ImageView imgTruck = binding.imgTruck;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                imgMiniCar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AddNewCarActivity.class);
                        intent.putExtra("category", "xe con");
                        startActivity(intent);
                    }
                });
                imgBus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AddNewCarActivity.class);
                        intent.putExtra("category", "xe khách");
                        startActivity(intent);
                    }
                });
                imgPickUpTruck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AddNewCarActivity.class);
                        intent.putExtra("category", "xe bán tải");
                        startActivity(intent);
                    }
                });
                imgTruck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AddNewCarActivity.class);
                        intent.putExtra("category", "xe tải");
                        startActivity(intent);
                    }
                });

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}