package com.example.adminappcarrental.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.adminappcarrental.AllCarFragment;
import com.example.adminappcarrental.Model.Car;
import com.example.adminappcarrental.R;
import com.example.adminappcarrental.Utils.Common;
import com.example.adminappcarrental.ViewPagerAdapter;
import com.example.adminappcarrental.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    RecyclerView listCar;
    String[] itemcar = null;
    private ArrayList<Car> listCar1 = new ArrayList<>();
    private ArrayList<Car> listCar2 = new ArrayList<>();
    private ArrayList<Car> listCar3 = new ArrayList<>();
    private ArrayList<Car> listCar4 = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Car car1 = dataSnapshot.getValue(Car.class);
                    String categoryCar = car1.getCategory();
                    if (categoryCar.equals(Common.CATEGORY_CAR_1)){
                        listCar1.add(car1);
                    }
                    if (categoryCar.equals(Common.CATEGORY_CAR_2)){
                        listCar2.add(car1);
                    }
                    if (categoryCar.equals(Common.CATEGORY_CAR_3)){
                        listCar3.add(car1);
                    }
                    if (categoryCar.equals(Common.CATEGORY_CAR_4)){
                        listCar4.add(car1);
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


//        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);

                itemcar = getResources().getStringArray(R.array.carlist);

                initViewPager();


            }
        });
        return root;
    }



    private void initViewPager() {
        viewPager = (requireView()).findViewById(R.id.viewpager);
        tabLayout = (requireView()).findViewById(R.id.tablayout1);
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerAdapter.addFragment(new AllCarFragment(),Common.CATEGORY_CAR_1);
        viewPagerAdapter.addFragment(new AllCarFragment(),Common.CATEGORY_CAR_2);
        viewPagerAdapter.addFragment(new AllCarFragment(),Common.CATEGORY_CAR_3);
        viewPagerAdapter.addFragment(new AllCarFragment(),Common.CATEGORY_CAR_4);



        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}