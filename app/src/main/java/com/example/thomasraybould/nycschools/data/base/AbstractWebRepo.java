package com.example.thomasraybould.nycschools.data.base;

import com.example.thomasraybould.nycschools.network.api_url_provider.ApiUrlProvider;
import com.example.thomasraybould.nycschools.network.auth_token_provider.AuthTokenProvider;
import com.example.thomasraybould.nycschools.network.http_client.HttpClient;

public abstract class AbstractWebRepo {

    protected final HttpClient httpClient;
    protected final AuthTokenProvider authTokenProvider;
    protected final ApiUrlProvider apiUrlProvider;

    public AbstractWebRepo(HttpClient httpClient, AuthTokenProvider authTokenProvider, ApiUrlProvider apiUrlProvider) {
        this.httpClient = httpClient;
        this.authTokenProvider = authTokenProvider;
        this.apiUrlProvider = apiUrlProvider;
    }

}
