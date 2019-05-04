package com.example.weatherapp.adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.SevenNextDays_Fragment;
import com.example.weatherapp.model.SevenNextDay;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SevenNextDayWeatherAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    ArrayList<SevenNextDay> model;

    public SevenNextDayWeatherAdapter(Context context, int layout, ArrayList<SevenNextDay> model) {
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
        SevenNextDayHolder holder = null;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new SevenNextDayHolder(view);
            view.setTag(holder);
        } else {
            holder = (SevenNextDayHolder) view.getTag();
        }

        SevenNextDay sevenNextDay = model.get(position);
        holder.populateFrom(sevenNextDay);

        return view;
    }

    class SevenNextDayHolder {

        TextView tvDayOfWeek;
        TextView tvDate;
        TextView tvStatus;
        ImageView imgViewIcon;
        TextView tvMaxTemp;
        TextView tvMinTemp;

        public SevenNextDayHolder(View view) {
            tvDayOfWeek = (TextView) view.findViewById(R.id.tvDayOfWeek);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            imgViewIcon = (ImageView) view.findViewById(R.id.imgViewIcon);
            tvMaxTemp = (TextView) view.findViewById(R.id.tvMaxTemp);
            tvMinTemp = (TextView) view.findViewById(R.id.tvMaxTemp);
        }

        public void populateFrom(SevenNextDay sevenNextDay) {
            tvDayOfWeek.setText(sevenNextDay.getDayOfWeek());
            tvDate.setText(sevenNextDay.getDate());
            tvStatus.setText(sevenNextDay.getStatus());
            tvMaxTemp.setText(sevenNextDay.getMaxTemp());
            tvMinTemp.setText(sevenNextDay.getMinTemp());
            Picasso.with(context).load("http://openweathermap.org/img/w/" + sevenNextDay.getIcon() + ".png").into(imgViewIcon);
        }

    }

}
