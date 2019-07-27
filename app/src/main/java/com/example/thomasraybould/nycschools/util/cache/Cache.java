package com.example.thomasraybould.nycschools.util.cache;

public interface Cache<T> {

    boolean contains(String key);

    T get(String key);

    void put(String key, T value);

}
