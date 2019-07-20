package com.example.thomasraybould.nycschools.network;

import android.arch.lifecycle.ViewModelProvider;

import org.json.JSONObject;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Predicate;
import okhttp3.OkHttpClient;

import static org.junit.Assert.*;

public class HttpClientImplTest {

    @Test
    public void getJson() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        HttpClientImpl httpClient = new HttpClientImpl(okHttpClient);

        String url = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json?dbn=02M260";

        NetworkRequest networkRequest = NetworkRequest.createNetworkRequest(url, null);

        httpClient.getJson(networkRequest)
                .test()
                .assertValue(jsonObjectNetworkResponse -> jsonObjectNetworkResponse.getData() != null)
                .assertComplete();
    }
}