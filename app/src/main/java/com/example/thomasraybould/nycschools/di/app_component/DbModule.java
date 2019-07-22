package com.example.thomasraybould.nycschools.di.app_component;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    private final Context context;

    public DbModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
     Context getContext(){
        return context;
    }

}
