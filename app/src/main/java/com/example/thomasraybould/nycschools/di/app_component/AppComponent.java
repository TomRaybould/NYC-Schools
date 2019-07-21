package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListPresenterImpl;

import dagger.Component;

@Component(modules = {NetworkModule.class,
        SchoolListModule.class,
        SatScoreModule.class,
        RxModule.class})
@AppScope
public interface AppComponent {

    void inject(SchoolListPresenterImpl schoolListActivity);
}
