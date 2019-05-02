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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.adater.DetailWeatherAdapter;
import com.example.weatherapp.model.DetailWeather;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tomorrow_Fragment extends Fragment {


    TextView tvCountry;
    TextView tvTime;
    TextView tvTemperature;
    TextView tvDescription;
    ImageView imgIcon;
    ListView lvDetails;
    String city = "Hanoi";

    DetailWeatherAdapter adapter;
    ArrayList<DetailWeather> model = new ArrayList<>();


    public Tomorrow_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tomorrow_, container, false);

        findView(view);

        //get city from bundle
        try {
            String citySearch = getArguments().getString("dataByHoangViet");
            this.city = citySearch;
            Log.d("myLogTomorrow", citySearch);

        } catch (NullPointerException ex) {
            //do-something
        }


        //default Hanoi's tomorrow weather
        getTomorrowWeather(city);


        return view;

    }

    private void getTomorrowWeather(String city) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?" +
                "q=" + city + "&lang=" + Locale.getDefault().getLanguage()
                + "&units=metric&cnt=2&appid=53fbf527d52d4d773e828243b90c1f8e";

        //get JSON from url by Volley library
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //country
                    JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                    String country = jsonObjectCity.getString("name") + "," + jsonObjectCity.getString("country");
                    tvCountry.setText(country);

                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                    JSONObject jsonObjectList = jsonArrayList.getJSONObject(1);

                    //time
                    String dt = jsonObjectList.getString("dt");
                    Long l = Long.valueOf(dt);
                    Date date = new Date(l * 1000);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
                    String time = simpleDateFormat.format(date);
                    tvTime.setText(time);

                    JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                    //current temp
                    String temp = jsonObjectTemp.getString("day");
                    double doubleTemp = Double.valueOf(temp);
                    tvTemperature.setText(String.valueOf((int) doubleTemp));

                    //get icon and description
                    JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);

                    //description
                    String description = jsonObjectWeather.getString("description");
                    tvDescription.setText(description);

                    //icon
                    String icon = jsonObjectWeather.getString("icon");
                    Picasso.with(getActivity()).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);

                    //detailWeather Tomorrow
                    String minTemp = jsonObjectTemp.getString("min");
                    double doubleMinTemp = Double.valueOf(minTemp);
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_minTemp),
                            String.valueOf((int) doubleMinTemp) + "*C"));

                    String maxTemp = jsonObjectTemp.getString("max");
                    double doubleMaxTemp = Double.valueOf(maxTemp);
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_maxTemp),
                            String.valueOf((int) doubleMaxTemp) + "*C"));

                    String pressure = jsonObjectList.getString("pressure");
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_pressure),
                            pressure + " hPa"));

                    String humisity = jsonObjectList.getString("humidity");
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_humidity),
                            humisity + " %"));

                    String windSpeed = jsonObjectList.getString("speed");
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_windSpeed),
                            windSpeed + " m/s"));

                    String clouds = jsonObjectList.getString("clouds");
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_cloud),
                            clouds + " %"));

                    String rain = jsonObjectList.getString("rain");
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_rain),
                            rain + " mm"));

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        adapter = new DetailWeatherAdapter(getContext(), R.layout.fragment_tomorrow_, model);
        lvDetails.setAdapter(adapter);

    }

}
