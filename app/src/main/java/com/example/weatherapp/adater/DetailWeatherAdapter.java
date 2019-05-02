package com.example.weatherapp.adater;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.DetailWeather;

import java.util.ArrayList;

public class DetailWeatherAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<DetailWeather> model;

    public DetailWeatherAdapter(Context context, int layout, ArrayList<DetailWeather> model) {
        this.context = context;
        this.layout = layout;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        DetailWeatherHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.detail_weather, null);
            holder = new DetailWeatherHolder(view);
            view.setTag(holder);
        } else {
            holder = (DetailWeatherHolder) view.getTag();
        }

        //get DetailWeather Object from position
        DetailWeather detailWeather = model.get(position);
        holder.populateFrom(detailWeather);

        return view;
    }

    class DetailWeatherHolder {
        TextView tvName;
        TextView tvValue;

        public DetailWeatherHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvValue = (TextView) view.findViewById(R.id.tvValue);
        }

        void populateFrom(DetailWeather detailWeather) {
            tvName.setText(detailWeather.getName().toString());
            tvValue.setText(detailWeather.getValue().toString());
        }
    }
}
