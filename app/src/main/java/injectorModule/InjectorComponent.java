package injectorModule;

import com.example.weatherapp.MainActivity;
import com.example.weatherapp.SearchPageActivity;
import com.example.weatherapp.WeatherApiService;

import javax.inject.Singleton;

import dagger.Component;
import services.ApiService;

@Component(modules = {InjectorModule.class})
@Singleton
public interface InjectorComponent {
     void inject(SearchPageActivity activity);
     void inject(MainActivity activity);
     void inject(WeatherApiService service);
}
