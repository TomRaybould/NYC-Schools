package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.view.MainActivity;

import dagger.Component;

@Component(modules = {NetworkModule.class, SchoolListModule.class, RxModule.class})
@AppScope
public interface AppComponent {

    void inject(MainActivity mainActivity);
}
