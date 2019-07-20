package com.example.thomasraybould.nycschools.network.http_client.impl;

import com.example.thomasraybould.nycschools.network.http_client.HttpClient;
import com.example.thomasraybould.nycschools.network.http_client.NetworkRequest;
import com.example.thomasraybould.nycschools.network.http_client.NetworkResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.Single;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpClientImpl implements HttpClient {

    public HttpClientImpl(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    private final OkHttpClient okHttpClient;

    @Override
    public Single<NetworkResponse<JSONObject>> getJson(final NetworkRequest networkRequest) {
        return get(networkRequest, JSON_DATA_TRANFORMER);
    }

    @Override
    public Single<NetworkResponse<JSONArray>> getJsonArray(NetworkRequest networkRequest) {
        return get(networkRequest, JSON_ARRAY_TRANFORMER);
    }

    private <T> Single<NetworkResponse<T>> get(final NetworkRequest networkRequest, DataTransformer<T> dataTransformer) {

        NetworkResponse<T> failure = NetworkResponse.failure();
        return Single.fromCallable(() -> {
            Request okRequest = networkRequestToOkRequest(networkRequest);
            Response response = null;
            try {
                response = okHttpClient.newCall(okRequest).execute();
            }catch (Exception e){
                e.printStackTrace();
            }
            if(response == null){
                return failure;
            }else{
                return parseResponse(response, dataTransformer);
            }
        }).onErrorReturnItem(failure);

    }


    private static Request networkRequestToOkRequest(NetworkRequest networkRequest){
        //default to empty headers
        Headers headers = new Headers.Builder().build();

        if(networkRequest.getHeaders() != null) {
            headers = Headers.of(networkRequest.getHeaders());
        }

        return new Request.Builder()
                .url(networkRequest.getUrl())
                .headers(headers)
                .build();
    }

    private static <T> NetworkResponse<T> parseResponse(Response response, DataTransformer<T> dataTransformer){
        if(!response.isSuccessful()){
            return NetworkResponse.failure();
        }

        ResponseBody body = response.body();
        String bodyString = null;
        try {
            if(body == null || (bodyString = body.string()) == null){
                return NetworkResponse.failure();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        T transformData = dataTransformer.transformData(bodyString);

        if(transformData == null){
            return NetworkResponse.failure();
        }

        return NetworkResponse.success(transformData);
    }

    private interface DataTransformer<T> {
        T transformData(String string);
    }

    private static DataTransformer<JSONObject> JSON_DATA_TRANFORMER = string -> {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(string);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    };

    private static DataTransformer<JSONArray> JSON_ARRAY_TRANFORMER = string -> {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(string);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return jsonArray;
    };

}
