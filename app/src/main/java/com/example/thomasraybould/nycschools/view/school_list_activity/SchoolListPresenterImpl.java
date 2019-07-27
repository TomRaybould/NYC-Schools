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
import com.example.thomasraybould.nycschools.util.cache.Cache;
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

    @Inject
    SchoolListCache schoolListItemCache;

    private final Map<String, Disposable> pendingDownloads = new HashMap<>();

    private final static String LIST_ITEMS_CACHE_KEY = "list_items_cache_key";

    @Override
    public void onCreate(SchoolListView view) {
        super.onCreate(view);

        //inject the presenter
        componentProvider.getAppComponent()
                .inject(this);

        setInitList();


    }

    private void setInitList() {
        List<SchoolListItem> schoolListItems = schoolListItemCache.get(LIST_ITEMS_CACHE_KEY);

        if(schoolListItems == null || schoolListItems.isEmpty()) {
            schoolListItems = new ArrayList<>();
            for (Borough borough : Borough.values()) {
                SchoolListItem boroughItem = SchoolListItem.createBoroughItem(borough);
                schoolListItems.add(boroughItem);
            }
        }

        view.setSchoolList(schoolListItems);
    }

    @Override
    public void onPause() {
        List<SchoolListItem> currentListItems = view.getCurrentListItems();
        schoolListItemCache.put(LIST_ITEMS_CACHE_KEY, currentListItems);
        pendingDownloads.clear();
        super.onPause();
    }

    @Override
    public void onSchoolListItemSelected(SchoolListItem schoolListItem) {
        if(schoolListItem.getType() == SchoolListItemType.BOROUGH_TITLE){
            onBoroughSelected(schoolListItem);
        }
        else if (schoolListItem.getType() == SchoolListItemType.SCHOOL_ITEM){
            onSchoolSelected(schoolListItem);
        }
    }

    private void onBoroughSelected(SchoolListItem schoolListItem) {
        Borough borough = schoolListItem.getBorough();
        //cancel download of json and remove school cells from list
        if(schoolListItem.isSelected()){
            view.removeItemsForBorough(borough);
            Disposable disposable = pendingDownloads.get(borough.code);
            if(disposable!= null){
                disposable.dispose();
            }
            view.changeBoroughLoadingStatus(borough, false);
            return;
        }

        view.changeBoroughLoadingStatus(borough, true);

        Disposable disposable = getSchoolListInteractor.getSchoolsByBorough(borough)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe(this::processGetSchoolListResponse,
                        throwable -> failedToLoadList(borough));

        pendingDownloads.put(borough.code, disposable);

        onPauseDisposable.add(disposable);
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

        view.changeBoroughLoadingStatus(borough, false);
        view.toast("Failed to load schools");
    }

    private List<SchoolListItem> schoolsToListItems(List<School> schools){
        List<SchoolListItem> schoolListItems = new ArrayList<>();
        for (School school: schools) {
            SchoolListItem schoolItem = SchoolListItem.createSchoolItem(school, school.getBorough());
            schoolListItems.add(schoolItem);
        }
        return schoolListItems;
    }

    private void onSchoolSelected(SchoolListItem schoolListItem){
        School school = schoolListItem.getSchool();
        if(schoolListItem.isSelected()){
            stopSatScoreRequest(school.getDbn());
            view.removeScoreItem(school.getDbn());
            return;
        }

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
