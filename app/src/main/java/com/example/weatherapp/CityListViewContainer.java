package com.example.weatherapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

public class CityListViewContainer extends RecyclerView.Adapter<CityListViewContainer.CityViewHolder> {

    private final SearchUtils utils;
    private final WeatherApiService weatherService;
    private List<String> sortedCities;

    public CityListViewContainer(WeatherApiService weatherService) {
        this.weatherService = weatherService;
      this.utils = new SearchUtils();

        this.sortedCities = this.utils.getAllCities().stream()
                .sorted((city1, city2) ->
                        city1.equals(this.weatherService.getSelectedCity()) ? -1 :
                                city2.equals(this.weatherService.getSelectedCity()) ? 1 : 0
                )
                .collect(Collectors.toList());
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_card, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        String city = this.sortedCities.get(position);
        holder.cityTitle.setText(city);

        if(city.equals(this.weatherService.getSelectedCity())){
            holder.itemView.setBackgroundResource(R.drawable.selected_card_bg);
            holder.cityTitle.setTextColor(Color.parseColor("#282929"));
            holder.itemView.setEnabled(false);
            holder.itemView.setAlpha(0.5f);
        }else{
            holder.itemView.setBackgroundResource(R.drawable.weather_card_bg);
            holder.cityTitle.setTextColor(Color.parseColor("#FBC27F"));
            holder.itemView.setEnabled(true);
            holder.itemView.setAlpha(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return this.sortedCities.size();
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView cityTitle;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTitle = itemView.findViewById(R.id.cityTitle);
        }
    }
}