package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.NycSchoolsApplication;
import com.example.thomasraybould.nycschools.di.school_list_component.SchoolListActivityModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Component(modules = {
        AndroidInjectionModule.class,
        ViewModelModule.class,
        SchoolListActivityModule.class,
        NetworkModule.class,
        SchoolListModule.class,
        SatScoreModule.class,
        RxModule.class,
        DbModule.class})
@Singleton
public interface AppComponent {
    void inject(NycSchoolsApplication nycSchoolsApplication);
}
