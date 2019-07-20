package com.example.thomasraybould.nycschools;

import android.app.Application;

import com.example.thomasraybould.nycschools.di.app_component.AppComponent;
import com.example.thomasraybould.nycschools.di.ComponentProviderImpl;
import com.example.thomasraybould.nycschools.di.app_component.DaggerAppComponent;

public class NycSchoolsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.create();

        ComponentProviderImpl.initComponentProvider(appComponent);
    }
}
