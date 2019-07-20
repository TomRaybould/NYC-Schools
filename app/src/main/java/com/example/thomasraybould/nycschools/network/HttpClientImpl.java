package com.example.thomasraybould.nycschools.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpClientImpl implements HttpClient{

    public HttpClientImpl(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    private final OkHttpClient okHttpClient;

    @Override
    public Single<NetworkResponse<JSONObject>> getJson(final NetworkRequest networkRequest) {

        NetworkResponse<JSONObject> failure = NetworkResponse.failure();
        return Single.fromCallable(new Callable<NetworkResponse<JSONObject>>() {
            @Override
            public NetworkResponse<JSONObject> call() throws Exception {

                Request okRequest = networkRequestToOkRequest(networkRequest);
                Response response = okHttpClient.newCall(okRequest).execute();
                return parseResponse(response);
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

    private static NetworkResponse<JSONObject> parseResponse(Response response){
        if(!response.isSuccessful()){
            return NetworkResponse.failure();
        }

        ResponseBody body = response.body();

        if(body == null){
            return NetworkResponse.failure();
        }

        JSONObject jsonObject = null;
        try {
            String string = body.string();
            jsonObject = new JSONObject(string);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonObject == null){
            return NetworkResponse.failure();
        }

        return NetworkResponse.success(jsonObject);
    }



}
