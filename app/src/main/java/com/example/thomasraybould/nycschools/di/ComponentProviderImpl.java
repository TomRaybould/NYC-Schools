package com.example.thomasraybould.nycschools.di;

import com.example.thomasraybould.nycschools.di.app_component.AppComponent;

/**
 * Holds reference to all dagger components for dependency injection in the app.
 */


public class ComponentProviderImpl implements ComponentProvider{

    private final AppComponent appComponent;

    private static ComponentProvider instance;

    private ComponentProviderImpl(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    public static void initComponentProvider(AppComponent appComponent) {
        instance = new ComponentProviderImpl(appComponent);
    }

    public static ComponentProvider getInstance() {
        return instance;
    }

    @Override
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
