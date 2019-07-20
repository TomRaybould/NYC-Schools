package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.example.thomasraybould.nycschools.network.http_client.HttpClient;

import dagger.Component;

@Component(modules = {NetworkModule.class, SchoolListModule.class})
@AppScope
public interface AppComponent {

    HttpClient httpClient();

    GetSchoolListInteractor fea();

}
