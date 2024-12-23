package injectorModule;

import com.example.weatherapp.WeatherApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import services.ApiService;

@Module
public class InjectorModule {
    @Provides
    ApiService provideApiService(){
        return new ApiService();
    }
    @Provides
    @Singleton
    WeatherApiService provideWeatherApiService(){
        return new WeatherApiService();
    }
}
