package com.example.thomasraybould.nycschools.data;

import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.example.thomasraybould.nycschools.entities.Borough;
import com.example.thomasraybould.nycschools.entities.School;
import com.example.thomasraybould.nycschools.network.FakeApiUrlProvider;
import com.example.thomasraybould.nycschools.network.TestHttpClient;
import com.example.thomasraybould.nycschools.network.api_url_provider.ApiUrlProvider;
import com.example.thomasraybould.nycschools.network.auth_token_provider.AuthTokenProvider;
import com.example.thomasraybould.nycschools.network.http_client.HttpClient;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class SchoolListWebRepoImplTest {
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
    public void getSchools() {
        SchoolListWebRepoImpl schoolListWebRepo = new SchoolListWebRepoImpl(httpClient, authTokenProvider, apiUrlProvider);

        for(Borough Borough : Borough.values()) {

            schoolListWebRepo.getSchoolsByBorough(Borough)
                    .test()
                    .assertValue(SchoolListResponse::isSuccessful)
                    .assertValue(schoolListResponse -> !schoolListResponse.getSchools().isEmpty())
                    .assertValue(schoolListResponse -> {
                        //checking I am getting the Boroughs I expect for the request
                        for (School school : schoolListResponse.getSchools()){
                            if(school.getBorough() != Borough){
                                return false;
                            }
                        }
                        return true;
                    })
                    .assertComplete();
        }
    }
}