package com.example.thomasraybould.nycschools.view.school_list_activity;

import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItem;
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor;
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data.SatDataResponse;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.example.thomasraybould.nycschools.entities.Borough;
import com.example.thomasraybould.nycschools.entities.SatScoreData;
import com.example.thomasraybould.nycschools.entities.School;
import com.example.thomasraybould.nycschools.rx_util.SchedulerProvider;
import com.example.thomasraybould.nycschools.view.base.AbstractRxPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class SchoolListPresenterImpl extends AbstractRxPresenter<SchoolListView> implements SchoolListPresenter {

    @Inject
    GetSchoolListInteractor getSchoolListInteractor;

    @Inject
    GetSatScoreDataInteractor getSatScoreDataInteractor;

    @Inject
    SchedulerProvider schedulerProvider;

    private final List<Borough> selectedBoroughs = new ArrayList<>();
    private final List<School>  selectedSchools = new ArrayList<>();

    private final Map<String, Disposable> pendingDownloads = new HashMap<>();

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

        //cancel download of json and remove school cells from list
        if(selectedBoroughs.contains(borough)){
            selectedBoroughs.remove(borough);
            removeSelectSchoolsForBorough(borough);
            view.removeItemsForBorough(borough);
            Disposable disposable = pendingDownloads.get(borough.code);
            if(disposable!= null){
                disposable.dispose();
            }
            view.changeBoroughLoadingStatus(borough, false);
            return;
        }

        view.changeBoroughLoadingStatus(borough, true);

        selectedBoroughs.add(borough);

        Disposable disposable = getSchoolListInteractor.getSchoolsByBorough(borough)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe(this::processGetSchoolListResponse,
                        throwable -> failedToLoadList(borough));

        pendingDownloads.put(borough.code, disposable);

        onPauseDisposable.add(disposable);
    }

    private void removeSelectSchoolsForBorough(Borough borough) {
        for (int i = selectedSchools.size() - 1; i >= 0; i--){
            School school = selectedSchools.get(i);
            if(school.getBorough().equals(borough)){
                stopSatScoreRequest(school.getDbn());
                selectedSchools.remove(i);
            }
        }
    }

    private void stopSatScoreRequest(String dbn){
        Disposable disposable = pendingDownloads.get(dbn);
        if(disposable != null){
            disposable.dispose();
        }
    }

    private void processGetSchoolListResponse(SchoolListResponse schoolListResponse){
        if(view == null){
            return;
        }
        if(!schoolListResponse.isSuccessful()){
            failedToLoadList(schoolListResponse.getBorough());
            return;
        }
        List<School> schools = schoolListResponse.getSchools();

        List<SchoolListItem> schoolListItems = schoolsToListItems(schools);
        view.addItemsForBorough(schoolListItems, schoolListResponse.getBorough());
        view.changeBoroughLoadingStatus(schoolListResponse.getBorough(), false);
    }

    private void failedToLoadList(Borough borough){
        if(view == null){
            return;
        }

        selectedBoroughs.remove(borough);
        view.changeBoroughLoadingStatus(borough, false);
        view.toast("Failed to load schools");
    }

    private List<SchoolListItem> schoolsToListItems(List<School> schools){
        List<SchoolListItem> schoolListItems = new ArrayList<>();
        for (School school: schools) {
            SchoolListItem schoolItem = SchoolListItem.createSchoolItem(school, school.getBorough(), () -> this.onSchoolSelected(school));
            schoolListItems.add(schoolItem);
        }
        return schoolListItems;
    }

    @Override
    public void onSchoolSelected(School school) {
        if(selectedSchools.contains(school)){
            stopSatScoreRequest(school.getDbn());
            view.removeScoreItem(school.getDbn());
            selectedSchools.remove(school);
            return;
        }
        selectedSchools.add(school);

        Disposable disposable = getSatScoreDataInteractor.getSatScoreDataByDbn(school.getDbn())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe(satDataResponse -> processSatScoreResponse(satDataResponse, school),
                        throwable -> failedToGetSatData(school));

        pendingDownloads.put(school.getDbn(), disposable);

        onPauseDisposable.add(disposable);
    }

    private void processSatScoreResponse(SatDataResponse satDataResponse, School school){
        if(view == null){
            return;
        }

        SatScoreData satScoreData = satDataResponse.getSatScoreData();
        if(!satDataResponse.isSuccessful() || satScoreData == null){
            failedToGetSatData(school);
            return;
        }

        SchoolListItem scoreListItem = satDataToSchoolListItem(satScoreData, school);

        view.addScoreItem(scoreListItem);
    }

    private void failedToGetSatData(School school){
        selectedSchools.remove(school);
        view.toast("Failed to load SAT scores");
    }

    private static SchoolListItem satDataToSchoolListItem(SatScoreData satScoreData, School school){

        return SchoolListItem.newBuilder()
                .borough(school.getBorough())
                .type(SchoolListItemType.SAT_SCORE_ITEM)
                .school(school)
                .satScoreData(satScoreData)
                .build();

    }

}
