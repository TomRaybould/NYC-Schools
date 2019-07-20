package com.example.thomasraybould.nycschools.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.RxUtil.SchedulerProvider;
import com.example.thomasraybould.nycschools.di.ComponentProvider;
import com.example.thomasraybould.nycschools.di.ComponentProviderImpl;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {


    @Inject
    GetSchoolListInteractor getSchoolListInteractor;

    @Inject
    SchedulerProvider schedulerProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ComponentProviderImpl.getInstance()
                .getAppComponent()
                .inject(this);

        Disposable disposable = getSchoolListInteractor.getSchools()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe(schoolListResponse -> {
                    if (schoolListResponse.isSuccessful()) {
                        toast("loaded list");
                    } else {
                        toast("failed to load");
                    }
                });

    }

    private void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
