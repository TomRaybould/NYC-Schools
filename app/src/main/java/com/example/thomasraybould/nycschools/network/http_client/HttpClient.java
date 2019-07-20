package com.example.thomasraybould.nycschools.network.http_client;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.Single;

/**
 * Interface to wrap my OkHttpClient implementation.
 */

public interface HttpClient {

    Single<NetworkResponse<JSONObject>> getJson(NetworkRequest networkRequest);

    Single<NetworkResponse<JSONArray>> getJsonArray(NetworkRequest networkRequest);

}
