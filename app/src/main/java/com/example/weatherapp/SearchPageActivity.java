package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import injectorModule.DaggerInjectorComponent;
import injectorModule.InjectorComponent;

public class SearchPageActivity extends AppCompatActivity {
@Inject
WeatherApiService weatherApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ((WeatherApplication) getApplication()).getInjectorComponent().inject(this);

        ImageButton mainButton = findViewById(R.id.mainButton);
       RecyclerView cityListContainer = findViewById(R.id.cityListContainer);

       cityListContainer.setLayoutManager(new LinearLayoutManager(this));
       CityListViewContainer viewContainer = new CityListViewContainer(this.weatherApiService);

       cityListContainer.setAdapter(viewContainer);

        mainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openWeatherPage();
            }
        });
    }

    private void openWeatherPage(){
        finish();
    }

    public void onSelectCity(View selectedCityContainer){
        TextView selectedCity = selectedCityContainer.findViewById(R.id.cityTitle);
        this.weatherApiService.setSelectedCity(selectedCity.getText().toString());
        this.openWeatherPage();
    }
}