package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import injectorModule.DaggerInjectorComponent;
import injectorModule.InjectorComponent;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import models.WeatherDTO;
import services.ApiService;

public class MainActivity extends AppCompatActivity {
    @Inject
    WeatherApiService weatherApiService;

    private List<WeatherDTO.WeatherData> weatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ((WeatherApplication) getApplication()).getInjectorComponent().inject(this);

        TextView weatherInfo = findViewById(R.id.weatherInfo);

        ImageButton searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             openSearchPage();
            }
        });


        weatherApiService.fetchWeatherData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<WeatherDTO>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                            }

                            @Override
                            public void onNext(@NonNull WeatherDTO weatherDTO) {
                                weatherList = weatherDTO.getList();
                                if (!weatherList.isEmpty()) {
                                    WeatherDTO.WeatherData firstWeatherData = weatherList.get(0);
                                    setMainWeatherData(firstWeatherData);

                                    RecyclerView weatherViewContainer = findViewById(R.id.weatherListViewContainer);
                                    weatherViewContainer.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                    weatherViewContainer.setAdapter(new WeatherListViewContainer(weatherDTO.getList()));
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                System.out.println(e.toString());
                                weatherInfo.setText("Упс, что-то пошло не так, попробуйте перезапустить приложение :(");
                            }

                            @Override
                            public void onComplete() {
                            }
                        }
                );

    }
    private void setMainWeatherData(WeatherDTO.WeatherData weatherData){
        String weatherDescription = WeatherUtils.parseWeather(weatherData);
        int weatherIcon = WeatherUtils.getWeatherImage(weatherData.getWeather().get(0));

        ImageView weatherImage = findViewById(R.id.weatherImage);
        TextView weatherInfo = findViewById(R.id.weatherInfo);

        weatherImage.setImageResource(weatherIcon);
        String selectedCity = this.weatherApiService.getSelectedCity();
        String fullDescription = "Город: "+selectedCity + "\n" + weatherDescription;
        weatherInfo.setText(fullDescription);
    }

    public void onSelectWeatherCard(View selectedCard){
        Long tagId =  (long)selectedCard.getTag();
        Optional<WeatherDTO.WeatherData> selectedWeatherData =
                this.weatherList.stream()
                        .filter(
                                weatherData -> weatherData.getDt() == tagId
                        ).findFirst();
        if(selectedWeatherData.isPresent()){
            this.setMainWeatherData(selectedWeatherData.get());
        }else{
            this.setMainWeatherData(this.weatherList.get(0));
        }
    }


    private void openSearchPage(){
        Intent intent = new Intent(this,SearchPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}