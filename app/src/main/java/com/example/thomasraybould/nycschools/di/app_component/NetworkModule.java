package com.example.thomasraybould.nycschools.di.app_component;

import com.example.thomasraybould.nycschools.network.api_url_provider.ApiUrlProvider;
import com.example.thomasraybould.nycschools.network.auth_token_provider.AuthTokenProvider;
import com.example.thomasraybould.nycschools.network.http_client.HttpClient;
import com.example.thomasraybould.nycschools.network.http_client.impl.HttpClientImpl;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    static OkHttpClient okHttpClient(){
       return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    static HttpClient httpClient(HttpClientImpl httpClient){
        return httpClient;
    }

    @Singleton
    @Provides
    static AuthTokenProvider authTokenProvider(){
        return () -> new HashMap<>();
    }

    @Singleton
    @Provides
    static ApiUrlProvider apiUrlProvider(){
        return new ApiUrlProvider() {
            @Override
            public String getSchoolListApi() {
                return "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";
            }

            @Override
            public String getSchoolSatApi() {
                return "https://data.cityofnewyork.us/resource/f9bf-2cp4.json";
            }
        };
    }


}
