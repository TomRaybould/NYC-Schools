package com.example.thomasraybould.nycschools.view.base;

import com.example.thomasraybould.nycschools.di.ComponentProvider;
import com.example.thomasraybould.nycschools.di.ComponentProviderImpl;

import io.reactivex.disposables.CompositeDisposable;


public class AbstractRxPresenter<T> implements Presenter<T>{

    protected T view;
    protected final CompositeDisposable onPauseDisposable = new CompositeDisposable();
    protected final CompositeDisposable onDestroyDisposable = new CompositeDisposable();
    protected final ComponentProvider componentProvider = ComponentProviderImpl.getInstance();

    @Override
    public void onCreate(T view) {
        this.view = view;
    }

    @Override
    public void onResume(T view) {
        this.view = view;
    }

    @Override
    public void onPause() {
        onPauseDisposable.clear();
        this.view = null;
    }


    @Override
    public void onDestroy() {
        onDestroyDisposable.clear();
        this.view = null;
    }

}
