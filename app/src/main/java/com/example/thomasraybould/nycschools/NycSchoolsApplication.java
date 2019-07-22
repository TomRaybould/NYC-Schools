package com.example.thomasraybould.nycschools;

import android.app.Application;

import com.example.thomasraybould.nycschools.di.app_component.AppComponent;
import com.example.thomasraybould.nycschools.di.ComponentProviderImpl;
import com.example.thomasraybould.nycschools.di.app_component.DaggerAppComponent;
import com.example.thomasraybould.nycschools.di.app_component.DbModule;

public class NycSchoolsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder()
                .dbModule(new DbModule(getApplicationContext()))
                .build();

        ComponentProviderImpl.initComponentProvider(appComponent);
    }
}
