package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListCache;

import dagger.Module;
import dagger.Provides;

@Module
public class SchoolListActivityModule {

    @AppScope
    @Provides
    static SchoolListCache getSchoolListItemsCache(){
        return new SchoolListCache();
    }

}
