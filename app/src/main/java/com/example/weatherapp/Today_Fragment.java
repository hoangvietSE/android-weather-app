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
import com.example.weatherapp.adater.DetailWeatherAdapter;
import com.example.weatherapp.model.DetailWeather;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    String city = "Hanoi";

    DetailWeatherAdapter adapter;
    ArrayList<DetailWeather> model = new ArrayList<>();


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

        } catch (NullPointerException ex) {
            //do-something
        }

        //default Hanoi's weather
        getCurrentWeather(city);


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
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    //Country
                    String country = jsonObject.getString("name");
                    country += ",";
                    JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                    country += jsonObjectSys.getString("country");
                    tvCountry.setText(country);

                    //time
                    String dt = jsonObject.getString("dt");
                    Long l = Long.valueOf(dt);
                    Date date = new Date(l * 1000);
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat(getResources().getString(R.string.timeEnglish_today));
                    String time = simpleDateFormatTime.format(date);
                    tvTime.setText(time);

                    //temperature
                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    String temperature = jsonObjectMain.getString("temp");
                    double doubleTemp = Double.valueOf(temperature);
                    tvTemperature.setText(String.valueOf((int) doubleTemp));

                    //Description and icon
                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    String icon = jsonObjectWeather.getString("icon");

                    Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + icon + ".png").into(imgIcon);
                    tvDescription.setText(description);


                    //detail weather
                    //min temp
                    String minTemp = jsonObjectMain.getString("temp_min");
                    double doubleMinTemp = Double.valueOf(minTemp);
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_minTemp),
                            String.valueOf((int) doubleMinTemp) + "*C"));

                    //max temp
                    String maxTemp = jsonObjectMain.getString("temp_max");
                    double doubleMaxTemp = Double.valueOf(maxTemp);
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_maxTemp),
                            String.valueOf((int) doubleMaxTemp) + "*C"));

                    //pressure
                    String pressure = jsonObjectMain.getString("pressure");
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_pressure),
                            pressure + " hPa"));

                    //humidity
                    String humidity = jsonObjectMain.getString("humidity");
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_humidity),
                            humidity + " %"));

                    //winSpeed
                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String winSpeed = jsonObjectWind.getString("speed");
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_windSpeed),
                            winSpeed + " m/s"));

                    //cloud
                    JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                    String cloud = jsonObjectCloud.getString("all");
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_cloud),
                            cloud + " %"));

                    //Sunrise
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aaa");
                    String dtSunRise = jsonObjectSys.getString("sunrise");
                    Long longSunRise = Long.valueOf(dtSunRise);
                    Date dateSunRise = new Date(longSunRise * 1000);
                    String timeSunRise = simpleDateFormat.format(dateSunRise);
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_sunrise), timeSunRise));

                    //Sunset
                    String dtSunSet = jsonObjectSys.getString("sunset");
                    Long longSunSet = Long.valueOf(dtSunSet);
                    Date dateSunSet = new Date(longSunSet * 1000);
                    String timeSunSet = simpleDateFormat.format(dateSunSet);
                    model.add(new DetailWeather(getResources().getString(R.string.detailWeather_sunset), timeSunSet));

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

        adapter = new DetailWeatherAdapter(getContext(), R.layout.detail_weather, model);
        lvDetails.setAdapter(adapter);
    }


}
