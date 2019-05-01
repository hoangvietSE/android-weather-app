package com.example.weatherapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Today_Fragment extends Fragment {

    TextView tvCountry;
    TextView tvTime;
    TextView tvTemperature;
    TextView tvDescription;
    ImageView imgIcon;
    ListView lvDetails;
    String city = null;


    public Today_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today_, container, false);

        //find View
        findView(view);

        //get city from bundle
        try {
            String citySearch = getArguments().getString("dataByHoangViet");
            this.city = citySearch;
            Log.d("myLog", citySearch);
        } catch (NullPointerException ex) {
            //do-something
        }
        if (city != null) {
            getCurrentWeather(city);
        }
        return view;
    }

    private void getCurrentWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&lang="
                + Locale.getDefault().getLanguage() + "&appid=cc70dbbe2e63f7890a1aac54bc902c9a";
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //handle JSON text format
                Log.d("myLog", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    private void findView(View view) {
        tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvTemperature = (TextView) view.findViewById(R.id.tvTemperature);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
        lvDetails = (ListView) view.findViewById(R.id.lvDetails);
    }


}
