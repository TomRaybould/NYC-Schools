package com.example.thomasraybould.nycschools.network.http_client;

/*
    With more time this response would hold more important data like
    Http status codes.
 */

public class NetworkResponse<T> {

    private final boolean   isSuccessful;
    private final T         data;

    private NetworkResponse(boolean isSuccessful, T data) {
        this.isSuccessful = isSuccessful;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    private static <T> NetworkResponse<T> createNetworkResponse(boolean isSuccessful, T data){
        return new NetworkResponse<>(isSuccessful, data);
    }

    public static <T> NetworkResponse<T> success(T data){
        return new NetworkResponse<>(true, data);
    }

    public static <T> NetworkResponse<T> failure(){
        return new NetworkResponse<>(false, null);
    }
}
