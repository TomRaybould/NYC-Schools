package com.example.thomasraybould.nycschools.network.http_client;

import java.util.Map;

public class NetworkRequest {

    private final String url;
    private final Map<String, String> headers;

    private NetworkRequest(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public static NetworkRequest createNetworkRequest(String url, Map<String, String> headers){
        return new NetworkRequest(url, headers);
    }
}
