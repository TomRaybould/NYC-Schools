package com.example.thomasraybould.nycschools.view.school_list_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data.SatDataResponse
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.SatScoreData
import com.example.thomasraybould.nycschools.entities.School
import com.example.thomasraybould.nycschools.rx_util.SchedulerProvider
import com.example.thomasraybould.nycschools.view.base.AbstractRxPresenter

import java.util.ArrayList
import java.util.HashMap

import javax.inject.Inject

import io.reactivex.disposables.Disposable

class SchoolListPresenterImpl : AbstractRxPresenter<SchoolListView>(), SchoolListPresenter {

    @Inject
    lateinit var getSchoolListInteractor: GetSchoolListInteractor

    @Inject
    lateinit var getSatScoreDataInteractor: GetSatScoreDataInteractor

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var schoolListItemCache: SchoolListCache

    private val schoolListItemUiModels: MutableList<SchoolListItemUiModel> = ArrayList()

    private val schoolListUiModelLiveData: MutableLiveData<SchoolListUiModel> = MutableLiveData()

    private val pendingDownloads = HashMap<String, Disposable>()

    override fun onCreate(view: SchoolListView) {
        super.onCreate(view)

        //inject the presenter
        componentProvider.appComponent
                .inject(this)

    }

    override fun getSchoolList(): LiveData<SchoolListUiModel> {
        for (borough in Borough.values()) {
            val boroughItem = SchoolListItemUiModel.createBoroughItem(borough)
            schoolListItemUiModels.add(boroughItem)
        }
        schoolListUiModelLiveData.postValue(SchoolListUiModel(schoolListItemUiModels))

        return schoolListUiModelLiveData
    }

    override fun onPause() {
        val currentListItems = view.currentListItems
        schoolListItemCache.put(LIST_ITEMS_CACHE_KEY, currentListItems)
        pendingDownloads.clear()
        super.onPause()
    }

    override fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel) {
        if (schoolListItemUiModel.type == SchoolListItemType.BOROUGH_TITLE) {
            onBoroughSelected(schoolListItemUiModel)
        } else if (schoolListItemUiModel.type == SchoolListItemType.SCHOOL_ITEM) {
            onSchoolSelected(schoolListItemUiModel)
        }
    }

    private fun onBoroughSelected(schoolListItemUiModel: SchoolListItemUiModel) {
        val borough = schoolListItemUiModel.borough
        //cancel download of json and remove school cells from list
        if (schoolListItemUiModel.isSelected) {
            for (i in schoolListItemUiModels.size - 1 downTo 0) {
                if (schoolListItemUiModels[i].borough == borough) {
                    if (schoolListItemUiModels[i].type == SchoolListItemType.BOROUGH_TITLE) {
                        schoolListItemUiModels[i].isLoading = false
                    } else {
                        schoolListItemUiModels.removeAt(i)
                    }
                }
            }
            schoolListUiModelLiveData.postValue(SchoolListUiModel(schoolListItemUiModels))
            val disposable = pendingDownloads[borough.code]
            disposable?.dispose()
            return
        }

        view.changeBoroughLoadingStatus(borough, true)

        val disposable = getSchoolListInteractor.getSchoolsByBorough(borough)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe({ this.processGetSchoolListResponse(it) },
                        { failedToLoadList(borough) })

        pendingDownloads[borough.code] = disposable

        onPauseDisposable.add(disposable)
    }

    private fun processGetSchoolListResponse(schoolListResponse: SchoolListResponse) {
        if (view == null) {
            return
        }
        if (!schoolListResponse.isSuccessful) {
            failedToLoadList(schoolListResponse.borough)
            return
        }

        val schools = schoolListResponse.schools

        var targetIdx = 0
        schoolListItemUiModels.forEachIndexed { index, schoolListItemUiModel ->
            if (schoolListItemUiModel.borough == schoolListResponse.borough) {
                schoolListItemUiModel.isLoading = false
                targetIdx = index
            }
        }

        val newSchoolListItemUiModels = schoolsToListItems(schools)
        schoolListItemUiModels.addAll(targetIdx + 1, newSchoolListItemUiModels)
        schoolListUiModelLiveData.postValue(SchoolListUiModel(schoolListItemUiModels))
    }

    private fun failedToLoadList(borough: Borough) {
        if (view == null) {
            return
        }

        view.changeBoroughLoadingStatus(borough, false)
        view.toast("Failed to load schools")
    }

    private fun schoolsToListItems(schools: List<School>): List<SchoolListItemUiModel> {
        val schoolListItemUiModels = ArrayList<SchoolListItemUiModel>()
        for (school in schools) {
            val schoolItem = SchoolListItemUiModel.createSchoolItem(school, school.borough)
            schoolListItemUiModels.add(schoolItem)
        }
        return schoolListItemUiModels
    }

    private fun onSchoolSelected(schoolListItemUiModel: SchoolListItemUiModel) {
        val school = schoolListItemUiModel.school
        if (schoolListItemUiModel.isSelected) {
            schoolListItemUiModels.remove(schoolListItemUiModel)
            schoolListUiModelLiveData.postValue(SchoolListUiModel(schoolListItemUiModels))
            return
        }

        val disposable = getSatScoreDataInteractor!!.getSatScoreDataByDbn(school.dbn)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe({ satDataResponse -> processSatScoreResponse(satDataResponse, school) },
                        { throwable -> failedToGetSatData(school) })

        pendingDownloads[school.dbn] = disposable

        onPauseDisposable.add(disposable)
    }

    private fun processSatScoreResponse(satDataResponse: SatDataResponse, school: School) {
        if (view == null) {
            return
        }

        val satScoreData = satDataResponse.satScoreData
        if (!satDataResponse.isSuccessful || satScoreData == null) {
            failedToGetSatData(school)
            return
        }

        val scoreListItem = satDataToSchoolListItem(satScoreData, school)

        view.addScoreItem(scoreListItem)
    }

    private fun failedToGetSatData(school: School) {
        view.toast("Failed to load SAT scores")
    }

    companion object {

        private val LIST_ITEMS_CACHE_KEY = "list_items_cache_key"

        private fun satDataToSchoolListItem(satScoreData: SatScoreData, school: School): SchoolListItemUiModel {

            return SchoolListItemUiModel.newBuilder()
                    .borough(school.borough)
                    .type(SchoolListItemType.SAT_SCORE_ITEM)
                    .school(school)
                    .satScoreData(satScoreData)
                    .build()

        }
    }

}
