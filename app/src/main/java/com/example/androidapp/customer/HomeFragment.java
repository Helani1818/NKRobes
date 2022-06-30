package com.example.androidapp.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidapp.R;

public class HomeFragment extends Fragment
{
    private HomeViewModel homeViewModel;
    ImageView dress1, dress2, dress3, dress4, blouse1, blouse2, skirt1, skirt2, lehenga1, lehenga2;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        dress1 =root.findViewById(R.id.dress_1);
        dress2 =root.findViewById(R.id.dress_2);
        dress3 =root.findViewById(R.id.dress_3);
        dress4 =root.findViewById(R.id.dress_4);
        skirt1 =root.findViewById(R.id.skirt_1);
        skirt2 =root.findViewById(R.id.skirt_2);
        blouse1 =root.findViewById(R.id.blouse_1);
        blouse2 =root.findViewById(R.id.blouse_2);
        lehenga1 =root.findViewById(R.id.lehenga_1);
        lehenga2 =root.findViewById(R.id.lehenga_2);

        skirt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), BlackSkirtActivity.class);
                startActivity (intent);
            }
        });

        skirt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), FloralSkirtActivity.class);
                startActivity (intent);
            }
        });

        dress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), BridesmaidDressActivity.class);
                startActivity (intent);
            }
        });

        dress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), CasualDressActivity.class);
                startActivity (intent);
            }
        });

        dress3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), LongDressActivity.class);
                startActivity (intent);
            }
        });

        dress4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), PartyDressActivity.class);
                startActivity (intent);
            }
        });

        blouse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), PinkCropTopActivity.class);
                startActivity (intent);
            }
        });

        blouse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), PinkLaceSareeJacketActivity.class);
                startActivity (intent);
            }
        });

        lehenga1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), DesignerLehengaActivity.class);
                startActivity (intent);
            }
        });

        lehenga2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getActivity (), BollywoodStyledLehengaActivity.class);
                startActivity (intent);
            }
        });


        return root;
    }
}
