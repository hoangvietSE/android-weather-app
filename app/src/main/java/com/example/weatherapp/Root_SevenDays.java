package com.example.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Root_SevenDays extends Fragment {


    public Root_SevenDays() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_root__seven_days, container, false);

        getFragmentManager().beginTransaction().replace(R.id.sevenDays, new SevenNextDays_Fragment()).commit();

        return view;
    }

}
