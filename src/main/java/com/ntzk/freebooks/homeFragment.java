package com.ntzk.freebooks;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;


public class homeFragment extends Fragment {
    private  View view;
    private SwitchCompat switchCompat;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =inflater.inflate(R.layout.home, container, false);
        switchCompat=view.findViewById(R.id.switcher);
        switchCompat.setChecked(((MainActivity)getActivity()).isNightModeOn());
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MainActivity)getActivity()).setNightMode(isChecked);
            }
        });
        Button descBtn=view.findViewById(R.id.desc_btn);
        descBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.toBookList);
            }
        });
        Button favMenuBtn=view.findViewById(R.id.favMenuBtn);
        favMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.homeToFavs);
            }
        });
        Button newsButton=view.findViewById(R.id.nou_btn);
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.homeToNews);
            }
        });
        Button contactButton=view.findViewById(R.id.contact_btn);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.homeToContact);
            }
        });
        return view;
    }

}