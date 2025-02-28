package com.example.thomasraybould.nycschools.di.school_list_component;

import com.example.thomasraybould.nycschools.features.school_list_activity.SchoolListActivity;
import com.example.thomasraybould.nycschools.features.school_list_compose_activity.SchoolListComposeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract public class SchoolListActivityModule {

    @ContributesAndroidInjector
    abstract SchoolListActivity contributeYourAndroidInjector();

    @ContributesAndroidInjector
    abstract SchoolListComposeActivity contributeSchoolListComposeActivityInjector();

}
