package com.example.thomasraybould.nycschools.network;

import org.json.JSONObject;

import io.reactivex.Single;

/**
 * Interface to wrap my OkHttpClient implementation.
 */

public interface HttpClient {

    Single<NetworkResponse<JSONObject>> getJson(NetworkRequest networkRequest);

}
