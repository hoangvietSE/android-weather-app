package com.example.weatherapp;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.adater.SevenNextDayWeatherAdapter;
import com.example.weatherapp.model.SevenNextDay;

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
public class SevenNextDays_Fragment extends Fragment {

    String city = "Hanoi";

    ArrayList<SevenNextDay> model;
    ListView lvSevenNextDay;
    SevenNextDayWeatherAdapter adapter;

    public SevenNextDays_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sevendays, container, false);

        setAdapterListView(view);

        //get city from bundle
        try {
            String citySearch = getArguments().getString("dataByHoangViet");
            this.city = citySearch;
            Log.d("myLogSeven", citySearch);

        } catch (NullPointerException ex) {
            //do-something
        }

        //get SenvenNextDay Weathe
        //default Hanoi's Weather
        getSevenNextDay(city);

        return view;
    }

    public void getSevenNextDay(String city) {
        //get JSON by url
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "&units=metric&lang="
                + Locale.getDefault().getLanguage() + "&cnt=8&appid=53fbf527d52d4d773e828243b90c1f8e";

        //to execute request
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayList = jsonObject.getJSONArray("list");

                    for (int i = 1; i < jsonArrayList.length(); i++) {
                        JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                        //dayOfWeek
                        String dt = jsonObjectList.getString("dt");
                        long l = Long.valueOf(dt);
                        Date date = new Date(l * 1000);

                        //dayOfWeek
                        SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("EEEE");
                        String dayOfWeek = simpleDateFormatDay.format(date);

                        //date
                        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd-MM-yyyy");
                        String Date = simpleDateFormatDate.format(date);

                        //status
                        JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                        String status = jsonObjectWeather.getString("description");

                        //temp
                        JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");
                        String maxTemp = jsonObjectTemp.getString("max");
                        double doubleMaxTemp = Double.valueOf(maxTemp);
                        maxTemp = String.valueOf((int) doubleMaxTemp) + "*C";

                        String minTemp = jsonObjectTemp.getString("min");
                        double doubleMinTemp = Double.valueOf(minTemp);
                        minTemp = String.valueOf((int) doubleMinTemp) + "*C";

                        String icon = jsonObjectWeather.getString("icon");

                        model.add(new SevenNextDay(dayOfWeek, Date, status, icon, maxTemp, minTemp));

                    }

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

    private void setAdapterListView(View view) {
        model = new ArrayList<>();
        adapter = new SevenNextDayWeatherAdapter(getContext(), R.layout.custom_sevendays_listview, model);
        lvSevenNextDay = (ListView) view.findViewById(R.id.lvSevenDays);
        lvSevenNextDay.setAdapter(adapter);
    }
}
