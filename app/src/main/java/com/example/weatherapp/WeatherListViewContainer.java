package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import lombok.NonNull;
import models.WeatherDTO;

public class WeatherListViewContainer extends RecyclerView.Adapter<WeatherListViewContainer.WeatherViewHolder>{

    private final List<WeatherDTO.WeatherData> weatherList;

    public WeatherListViewContainer(List<WeatherDTO.WeatherData> weatherList) {
        this.weatherList = weatherList;
    }



    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_card, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull WeatherViewHolder holder,
            int position
    ) {
        WeatherDTO.WeatherData data = weatherList.get(position);
        LocalDateTime localDate = WeatherUtils.parseDate(data.getDtTxt());
        String weatherDay = localDate.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("ru"));

        holder.weatherDay.setText(
                weatherDay + "\n" +
                        "в " + localDate.getHour() + ".00"
                );

        double temp = Math.round(data.getMain().getTemp());
        holder.weatherTemp.setText(Math.round(temp) + " C");
        int weatherIcon = WeatherUtils.getWeatherImage(
                data.getWeather().get(0)
        );

        holder.weatherIcon.setImageResource(weatherIcon);
        holder.itemView.setTag(data.getDt());
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView weatherDay, weatherTemp;
        ImageView weatherIcon;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherDay = itemView.findViewById(R.id.weatherDay);
            weatherTemp = itemView.findViewById(R.id.weatherTemp);
            weatherIcon = itemView.findViewById(R.id.weatherIcon);
        }

}}
