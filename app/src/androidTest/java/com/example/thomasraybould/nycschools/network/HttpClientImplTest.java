package com.example.thomasraybould.nycschools.network;

import com.example.thomasraybould.nycschools.network.http_client.HttpClient;
import com.example.thomasraybould.nycschools.network.http_client.NetworkRequest;

import org.junit.Test;

public class HttpClientImplTest {

    @Test
    public void getJson() {

        HttpClient httpClient = TestHttpClient.getHttpClient();

        String url = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json?dbn=02M260";

        NetworkRequest networkRequest = NetworkRequest.Companion.createNetworkRequest(url, null);

        httpClient.getJsonArray(networkRequest)
                .test()
                .assertValue(jsonObjectNetworkResponse -> jsonObjectNetworkResponse.getData() != null)
                .assertComplete();
    }
}