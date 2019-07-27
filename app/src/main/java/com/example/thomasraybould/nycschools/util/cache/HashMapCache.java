package com.example.thomasraybould.nycschools.util.cache;

import java.util.HashMap;
import java.util.Map;

public class HashMapCache<T> implements Cache<T> {

    private final Map<String, T> cacheMap = new HashMap<>();

    @Override
    public boolean contains(String key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public T get(String key) {
        return cacheMap.get(key);
    }

    @Override
    public void put(String key, T value) {
        cacheMap.put(key, value);
    }
}
