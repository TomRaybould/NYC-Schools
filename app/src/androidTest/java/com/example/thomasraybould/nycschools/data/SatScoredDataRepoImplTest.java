package com.example.thomasraybould.nycschools.data;


import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.SatScoredDataRepo;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data.SatDataResponse;
import com.example.thomasraybould.nycschools.network.FakeApiUrlProvider;
import com.example.thomasraybould.nycschools.network.TestHttpClient;
import com.example.thomasraybould.nycschools.network.api_url_provider.ApiUrlProvider;
import com.example.thomasraybould.nycschools.network.auth_token_provider.AuthTokenProvider;
import com.example.thomasraybould.nycschools.network.http_client.HttpClient;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class SatScoredDataRepoImplTest {

    private HttpClient httpClient;
    private AuthTokenProvider authTokenProvider;
    private ApiUrlProvider apiUrlProvider;

    @Before
    public void setUp() {
        httpClient = TestHttpClient.getHttpClient();

        authTokenProvider = HashMap::new;
        apiUrlProvider = FakeApiUrlProvider.getApiUrlProvider();
    }

    @Test
    public void getSatScores() {
        SatScoredDataRepo satScoredDataRepo = new SatScoredDataRepoImpl(httpClient, authTokenProvider, apiUrlProvider);

        String dbn = "01M292";

        satScoredDataRepo.getSatScoreDataByDbn(dbn)
                .test()
                .assertValue(SatDataResponse::isSuccessful)
                .assertValue(satDataResponse -> satDataResponse.getSatScoreData().getDbn().equals(dbn))
                .assertComplete();

    }


}