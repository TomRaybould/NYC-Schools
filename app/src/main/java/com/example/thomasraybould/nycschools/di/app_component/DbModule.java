package com.example.thomasraybould.nycschools.di.app_component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    private final Context context;

    public DbModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
     Context getContext(){
        return context;
    }

}
