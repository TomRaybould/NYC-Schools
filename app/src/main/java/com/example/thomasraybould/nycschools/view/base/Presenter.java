package com.example.thomasraybould.nycschools.view.base;

public interface Presenter<T> {

    void onCreate(T view);

    void onResume(T view);

    void onPause();

    void onDestroy();
}
