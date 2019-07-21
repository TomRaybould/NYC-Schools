package com.example.thomasraybould.nycschools.network;

import com.example.thomasraybould.nycschools.network.api_url_provider.ApiUrlProvider;

public class FakeApiUrlProvider {

    public static ApiUrlProvider getApiUrlProvider(){

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
