package com.example.thomasraybould.nycschools.di.app_component;

import dagger.Component;

@Component(modules = {NetworkModule.class, SchoolListModule.class, RxModule.class})
@AppScope
public interface AppComponent {

}
