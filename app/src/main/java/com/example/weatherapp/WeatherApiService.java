package com.example.weatherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import injectorModule.DaggerInjectorComponent;
import injectorModule.InjectorComponent;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import models.CityDTO;
import models.WeatherDTO;
import services.ApiService;

@Singleton
public class WeatherApiService extends Service {
    @Inject
    ApiService apiService;

    private final BehaviorSubject<String> selectedCity$ = BehaviorSubject.createDefault("Минск");

public void setSelectedCity(String selectedCity){
    this.selectedCity$.onNext(selectedCity);
}

public String getSelectedCity(){
    return this.selectedCity$.getValue();
}
    public WeatherApiService() {
        InjectorComponent injector = DaggerInjectorComponent.create();
        injector.inject(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Observable<WeatherDTO> fetchWeatherData(){
        Type cityListType = Types.newParameterizedType(List.class, CityDTO.class);
       return this.selectedCity$.switchMap(
               city ->apiService.get(
                               "https://api.openweathermap.org/geo/1.0/direct?q="+city +",BY&limit=1&appid=0b66b449f87d63dca507d74701bc418b",
                               cityListType
                       ).toObservable()
                       .switchMap(
                               cityDTOList ->{
                                   List<CityDTO> cityList = (List<CityDTO>) cityDTOList;

                                   if (cityList.isEmpty()) {
                                       return Observable.error(new Throwable("City list is empty"));
                                   }
                                   CityDTO cityDTO = cityList.get(0);
                                   return apiService.get(
                                           "https://api.openweathermap.org/data/2.5/forecast?lat=" + cityDTO.getLat() + "&lon=" + cityDTO.getLon() + "&units=metric&appid=0b66b449f87d63dca507d74701bc418b"
                                           ,WeatherDTO.class).toObservable().map(v ->{
                                       WeatherDTO res = (WeatherDTO) v;
                                       return  res;
                                   });

                               }
                       )
       );
    }
}