package com.example.weatherapi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.weatherapi.R;
import com.example.weatherapi.data.Example;
import com.example.weatherapi.data.WeatherModel;
import com.example.weatherapi.databinding.ActivityMainBinding;
import com.example.weatherapi.internet.RetrofitBuilder;
import com.example.weatherapi.internet.WeatherApi;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomSheetBehavior sheetBehavior;
    private ImageView header_Arrow_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayout mBottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
        header_Arrow_Image = findViewById(R.id.bottom_sheet_arrow);
        EditText cityName = findViewById(R.id.edit_enter_city_name);
        Button btnShowResult = findViewById(R.id.btn_show_result);

        btnShowResult.setOnClickListener(new View.OnClickListener() {

            final String apiKey = "b211aae5545e8d3de75404d096930c95";

            @Override
            public void onClick(View v) {
                RetrofitBuilder.getInstance().getCurrentWeather(cityName.getText().toString().trim(), apiKey).enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            WeatherModel model = response.body().getModel();
                            Double temp = model.getTemp();
                            Integer humidity = model.getHumidity();
                            Integer pressure = model.getPressure();
                            Double tempMax = model.getTempMax();
                            Double tempMin = model.getTempMin();

                            Integer tMax = (int) (tempMax - 273.15);
                            Integer tMin = (int) (tempMin - 273.15);
                            Integer pressure_mBar = pressure / 1000;
                            Integer temperature = (int) (temp - 273.15);

                            Log.d("tag", "success: " + temp);

                            binding.txtGradus.setText(String.valueOf(temperature));
                            binding.txtHumidityPercent.setText(String.valueOf(humidity) + "%");
                            binding.txtBarometerMBar.setText(String.valueOf(pressure_mBar) + "mBar");
                            binding.tempMax.setText(String.valueOf(tMax) + "℃");
                            binding.tempMin.setText(String.valueOf(tMin) + "℃");
                        } else {
                            Log.d("tag", "error: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Log.d("tag", "failure: " + t.getLocalizedMessage());
                    }
                });

                RetrofitBuilder.getInstance().getWeather(cityName.getText().toString().trim()).enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            Log.d("tag", "success :" + response.body().getName());
                            binding.txtResultSearch.setText(response.body().getName());

                        } else {
                            Log.d("tag", "Error! :" + response.code());

                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {
                        Log.d("tag", "Failure! :" + t.getLocalizedMessage());

                    }

                });
            }
        });

        showBottomSheetDialog();
        setRotationForArrow();
    }


    private void setRotationForArrow() {
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                header_Arrow_Image.setRotation(slideOffset * 180);
            }
        });
    }

    private void showBottomSheetDialog() {
        header_Arrow_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }


}