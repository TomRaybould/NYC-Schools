package com.example.thomasraybould.nycschools.features.base;

import androidx.lifecycle.ViewModel;

import com.example.thomasraybould.nycschools.di.ComponentProvider;
import com.example.thomasraybould.nycschools.di.ComponentProviderImpl;

import io.reactivex.disposables.CompositeDisposable;


public class BaseViewModel extends ViewModel {

    protected final CompositeDisposable onPauseDisposable = new CompositeDisposable();
    protected final CompositeDisposable onDestroyDisposable = new CompositeDisposable();
    protected final ComponentProvider componentProvider = ComponentProviderImpl.getInstance();

}
