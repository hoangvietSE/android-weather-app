package com.example.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tomorrow_Fragment extends Fragment {

    String city = null;


    public Tomorrow_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tomorrow_, container, false);


        //get city from bundle
        try {
            String citySearch = getArguments().getString("dataByHoangViet");
            this.city = citySearch;
            Log.d("myLogTomorrow", citySearch);

        } catch (NullPointerException ex) {
            //do-something
        }
        //getCurrentWeather(city);

        return view;

    }

}
