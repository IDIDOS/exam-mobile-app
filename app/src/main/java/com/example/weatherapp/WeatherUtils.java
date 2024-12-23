package com.example.weatherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import models.WeatherDTO;

public class WeatherUtils extends Service {
    public WeatherUtils() {
    }

    private  static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static String parseWeather( WeatherDTO.WeatherData weatherData){
       double temp = weatherData.getMain().getTemp();
       double feelsLikeTemp = weatherData.getMain().getFeelsLike();
       String date = weatherData.getDtTxt();
       LocalDateTime weatherDate = WeatherUtils.parseDate(date);
        return "Температура: "+Math.round(temp) + " C" + "\n" +
                "Ощущается как: " + Math.round(feelsLikeTemp) +" C" + "\n" +
                "День: "+ weatherDate.getDayOfMonth() + "." + weatherDate.getMonthValue() + "\n" +
                "Время: " + weatherDate.getHour() + ".00" ;

    }

    public static int getWeatherImage(WeatherDTO.Weather weatherDescription){
        String weatherName =weatherDescription.getMain();

        return WeatherUtils.weatherMapping(weatherName);
    }

    private  static int weatherMapping(String weatherName){
        String weatherKey = weatherName.toLowerCase();

        if(weatherKey.equals("clouds")) {
        return R.mipmap.clouds_icon;
        }
        if(weatherKey.equals("rain")) {
        return R.mipmap.rain_icon;
        }
        if(weatherKey.equals("snow")){
            return  R.mipmap.snow_icon;
        }
        return R.mipmap.sun_icon;
    }

    public static LocalDateTime  parseDate(String date){
        LocalDateTime dateTime = LocalDateTime.parse(date,WeatherUtils.formatter);
        return dateTime;
    }
}