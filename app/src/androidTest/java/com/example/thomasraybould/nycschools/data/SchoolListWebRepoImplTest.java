package com.example.thomasraybould.nycschools.data;

import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.example.thomasraybould.nycschools.network.TestHttpClient;
import com.example.thomasraybould.nycschools.network.api_url_provider.ApiUrlProvider;
import com.example.thomasraybould.nycschools.network.auth_token_provider.AuthTokenProvider;
import com.example.thomasraybould.nycschools.network.http_client.HttpClient;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

public class SchoolListWebRepoImplTest {
    private HttpClient httpClient;
    private AuthTokenProvider authTokenProvider;
    private ApiUrlProvider apiUrlProvider;

    @Before
    public void setUp() {
        httpClient = TestHttpClient.getHttpClient();

        authTokenProvider = HashMap::new;
        apiUrlProvider = () -> "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";
    }

    @Test
    public void getSchools() {

        SchoolListWebRepoImpl schoolListWebRepo = new SchoolListWebRepoImpl(httpClient, authTokenProvider, apiUrlProvider);

        schoolListWebRepo.getSchools()
                .test()
                .assertValue(SchoolListResponse::isSuccessful)
                .assertValue(schoolListResponse -> !schoolListResponse.getSchools().isEmpty())
                .assertComplete();

    }
}