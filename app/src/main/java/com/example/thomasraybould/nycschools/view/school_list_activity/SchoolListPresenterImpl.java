package com.example.thomasraybould.nycschools.view.school_list_activity;

import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItem;
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

        setInitList();


    }

    private void setInitList() {
        List<SchoolListItem> schoolListItems = new ArrayList<>();
        for(Borough borough : Borough.values()){
            SchoolListItem boroughItem = SchoolListItem.createBoroughItem(borough, ()-> onBoroughSelected(borough));
            schoolListItems.add(boroughItem);
        }

        view.setSchoolList(schoolListItems);
    }


    @Override
    public void onBoroughSelected(Borough borough) {

        Disposable disposable = getSchoolListInteractor.getSchoolsByBorough(borough)
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

        List<SchoolListItem> schoolListItems = schoolsToListItems(schools);
        view.setSchoolList(schoolListItems);
    }



    private void failedToLoadList(){
        if(view == null){
            return;
        }
        view.setSchoolList(new ArrayList<>());
    }

    private static List<SchoolListItem> schoolsToListItems(List<School> schools){
        List<SchoolListItem> schoolListItems = new ArrayList<>();
        for (School school: schools) {
            schoolListItems.add(SchoolListItem.createSchoolItem(school.getName()));
        }
        return schoolListItems;
    }

}
