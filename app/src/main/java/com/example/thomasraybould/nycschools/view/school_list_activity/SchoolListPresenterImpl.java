package com.example.thomasraybould.nycschools.view.school_list_activity;

import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.example.thomasraybould.nycschools.entities.Borough;
import com.example.thomasraybould.nycschools.entities.School;
import com.example.thomasraybould.nycschools.rx_util.SchedulerProvider;
import com.example.thomasraybould.nycschools.view.base.AbstractRxPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class SchoolListPresenterImpl extends AbstractRxPresenter<SchoolListView> implements SchoolListPresenter {

    @Inject
    GetSchoolListInteractor getSchoolListInteractor;

    @Inject
    SchedulerProvider schedulerProvider;


    @Override
    public void onCreate(SchoolListView view) {
        super.onCreate(view);

        //inject the presenter
        componentProvider.getAppComponent()
                .inject(this);

        Disposable disposable = getSchoolListInteractor.getSchoolsByBorough(Borough.MANHATTAN)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe(this::processGetSchoolListResponse);

        onPauseDisposable.add(disposable);
    }

    private void processGetSchoolListResponse(SchoolListResponse schoolListResponse){
        if(!schoolListResponse.isSuccessful()){
            failedToLoadList();
        }
        List<School> schools = schoolListResponse.getSchools();
        view.setSchoolList(schools);
    }

    private void failedToLoadList(){
        if(view == null){
            return;
        }
        view.setSchoolList(new ArrayList<>());
    }

}
