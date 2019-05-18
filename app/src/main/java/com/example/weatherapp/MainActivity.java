package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem itemToday;
    TabItem itemTomorrow;
    TabItem itemSevenDays;
    ViewPager viewPager;
    ViewPagerAdapter pagerAdapter;
    LinearLayout linearLayout;

    TextView tvCity;
    EditText edtCity;

    SharedPreferences sharedPreferences;//save temp_City in editText of user

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        //can create the file(mySetting) and call by calling application (this application)
        sharedPreferences = getSharedPreferences("mySetting", Context.MODE_PRIVATE);

        //Event when user click on TabItem
        //Sync Tablayout with ViewPager
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    tvCity.setEnabled(true);
                    edtCity.setEnabled(true);

                    //change-color
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                } else if (tab.getPosition() == 1) {
                    tvCity.setEnabled(false);
                    edtCity.setEnabled(false);

                    //send data from activity to fragment
                    Bundle bundle = new Bundle();
                    String city = edtCity.getText().toString();
                    if (city.length() == 0) {
                        city = "Hanoi";
                    }
                    bundle.putString("dataByHoangViet", city);
                    Tomorrow_Fragment tomorrow_fragment = new Tomorrow_Fragment();
                    tomorrow_fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.tomorrow, tomorrow_fragment)
                            .commit();
                    //change-color
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark_two));
                    }
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.colorTablayout));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorTablayout));
                } else {
                    tvCity.setEnabled(false);
                    edtCity.setEnabled(false);

                    //send data from activity to fragment
                    Bundle bundle = new Bundle();
                    String city = edtCity.getText().toString();
                    if (city.length() == 0) {
                        city = "Hanoi";
                    }
                    bundle.putString("dataByHoangViet", city);
                    SevenNextDays_Fragment sevenNextDays_fragment = new SevenNextDays_Fragment();
                    sevenNextDays_fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.sevenDays, sevenNextDays_fragment)
                            .commit();
                    //change-color
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //do-something
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //do-something
            }
        });
        //event when users swipe to left or to right
        //sync viewPager with TabLayout
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final FragmentManager fragmentManager = getSupportFragmentManager();
                //final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //Transfer String city from activity to Fragment
                try {
                    Bundle bundle = new Bundle();
                    String city = edtCity.getText().toString();
                    if (city.length() == 0) {
                        city = "Hanoi";
                    }
                    bundle.putString("dataByHoangViet", city);
                    Today_Fragment today_fragment = new Today_Fragment();
                    //Tomorrow_Fragment tomorrow_fragment = new Tomorrow_Fragment();
                    //SevenNextDays_Fragment sevenDays_fragment = new SevenNextDays_Fragment();
                    today_fragment.setArguments(bundle);
                    //tomorrow_fragment.setArguments(bundle);
                    //sevenDays_fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.today, today_fragment).commit();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.tomorrow, tomorrow_fragment).commit();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.tomorrow, sevenDays_fragment).commit();
                } catch (IllegalArgumentException ex) {
                    //do-something
                }
            }
        });

        edtCity.setText(sharedPreferences.getString("cityByEditText", ""));
        edtCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do-something
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //do-something
            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cityByEditText", edtCity.getText().toString());
                editor.commit();
            }
        });
    }

    private void findView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        itemToday = (TabItem) findViewById(R.id.itemToday);
        itemTomorrow = (TabItem) findViewById(R.id.itemTomorrow);
        itemSevenDays = (TabItem) findViewById(R.id.itemSevenDays);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        edtCity = (EditText) findViewById(R.id.edtCity);
        tvCity = (TextView) findViewById(R.id.tvCity);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

}
