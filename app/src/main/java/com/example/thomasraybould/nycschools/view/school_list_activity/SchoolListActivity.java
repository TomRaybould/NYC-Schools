package com.example.thomasraybould.nycschools.view.school_list_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.entities.School;
import com.example.thomasraybould.nycschools.rx_util.SchedulerProvider;
import com.example.thomasraybould.nycschools.di.ComponentProviderImpl;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class SchoolListActivity extends AppCompatActivity implements SchoolListView {

    SchoolListPresenter schoolListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        schoolListPresenter = new SchoolListPresenterImpl();
        schoolListPresenter.onCreate(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        schoolListPresenter.onResume(this);
    }

    @Override
    protected void onPause() {
        schoolListPresenter.onPause();
        super.onPause();
    }

    private void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSchoolList(List<School> schools) {
        if (!schools.isEmpty()) {
            toast("loaded list");
        } else {
            toast("failed to load");
        }
    }
}
