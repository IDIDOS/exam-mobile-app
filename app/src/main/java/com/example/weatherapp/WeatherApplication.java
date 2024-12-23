package com.example.weatherapp;

import android.app.Application;

import injectorModule.DaggerInjectorComponent;
import injectorModule.InjectorComponent;
import injectorModule.InjectorModule;

public class WeatherApplication  extends Application {
    private InjectorComponent injector;
    @Override
    public void onCreate() {
        super.onCreate();
        injector = DaggerInjectorComponent.builder()
                .injectorModule(new InjectorModule())
                .build();
    }

    public InjectorComponent getInjectorComponent() {
        return injector;
    }
}
