package com.example.thomasraybould.nycschools.features.school_list_compose_activity.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data.SatDataResponse
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.School
import com.example.thomasraybould.nycschools.rx_util.SchedulerProvider
import com.example.thomasraybould.nycschools.features.base.BaseViewModel
import com.example.thomasraybould.nycschools.features.school_list_compose_activity.ComposeSchoolListUiModel
import com.example.thomasraybould.nycschools.features.uiModels.NycListItem
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ComposeSchoolListViewModelImpl @Inject constructor(
    private val getSchoolListInteractor: GetSchoolListInteractor,
    val getSatScoreDataInteractor: GetSatScoreDataInteractor,
    val schedulerProvider: SchedulerProvider
) : BaseViewModel(), ComposeSchoolListViewModel {

    private val composeSchoolListUiModelLiveData: MutableLiveData<ComposeSchoolListUiModel> = MutableLiveData()

    private val pendingDownloads = HashMap<String, Disposable>()

    private fun postUpdatedList(nycListItems: List<NycListItem>) {
        composeSchoolListUiModelLiveData.postValue(ComposeSchoolListUiModel(nycListItems))
    }

    private fun postWithError(error: String) {
        composeSchoolListUiModelLiveData.postValue(ComposeSchoolListUiModel(listOf(), error))
    }

    override fun getSchoolList(): LiveData<ComposeSchoolListUiModel> {
        val nycListItems = getCurrentList()
        if (nycListItems.isEmpty()) {
            val boroughItemUiModels = Borough.entries.map { borough ->
                NycListItem.BoroughItemUiModel(borough)
            }
            postUpdatedList(boroughItemUiModels)
        }

        return composeSchoolListUiModelLiveData
    }

    override fun onSchoolListItemSelected(nycListItem: NycListItem) {
        if (nycListItem is NycListItem.BoroughItemUiModel) {
            onBoroughSelected(nycListItem)
        } else if (nycListItem is NycListItem.SchoolItemUiModel) {
            onSchoolSelected(nycListItem)
        }
    }

    private fun onBoroughSelected(boroughItemUiModel: NycListItem.BoroughItemUiModel) {
        val borough = boroughItemUiModel.borough
        //cancel download of json and remove school cells from list
        if (boroughItemUiModel.isSelected) {
            val newList = getCurrentList().toMutableList()
            for (i in newList.size - 1 downTo 0) {
                val nycListItem = newList[i]
                if (nycListItem.borough == borough) {
                    if (nycListItem is NycListItem.BoroughItemUiModel) {
                        newList[i] = nycListItem.copy(isLoading = false, isSelected = false)
                    } else {
                        newList.removeAt(i)
                    }
                }
            }

            postUpdatedList(newList)
            val disposable = pendingDownloads[borough.code]
            disposable?.dispose()
            return
        }


        val newList = getCurrentList().map {
            if (it is NycListItem.BoroughItemUiModel && it.borough == borough) {
                return@map it.copy(isSelected = true, isLoading = true)
            }
            it
        }


        postUpdatedList(newList)

        val disposable = getSchoolListInteractor.getSchoolsByBorough(borough)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribe({ this.processGetSchoolListResponse(it, borough) },
                { failedToLoadList(borough) })

        pendingDownloads[borough.code] = disposable

        onDestroyDisposable.add(disposable)
    }

    private fun processGetSchoolListResponse(
        schoolListResponse: SchoolListResponse,
        borough: Borough
    ) {
        if (!schoolListResponse.isSuccessful) {
            failedToLoadList(borough)
            return
        }

        val schools = schoolListResponse.schools

        var targetIdx = 0

        getCurrentList().forEachIndexed { index, schoolListItemUiModel ->
            if (schoolListItemUiModel is NycListItem.BoroughItemUiModel &&
                schoolListItemUiModel.borough == schoolListResponse.borough
            ) {
                schoolListItemUiModel.isLoading = false
                targetIdx = index
            }
        }

        val newSchoolListItemUiModels = schoolsToListItems(schools)

        val newList = getCurrentList().apply {
            set(targetIdx, NycListItem.BoroughItemUiModel(borough, isSelected = true))
            addAll(targetIdx + 1, newSchoolListItemUiModels)
        }
        postUpdatedList(newList)
    }

    private fun failedToLoadList(borough: Borough) {
        val newList = getCurrentList().map {
            if (it is NycListItem.BoroughItemUiModel && it.borough == borough) {
                it.isLoading = false
                it
            }
        }

        postWithError("Failed to load schools")
    }

    private fun schoolsToListItems(schools: List<School>): List<NycListItem> {
        val schoolListItemUiModels = mutableListOf<NycListItem>()
        for (school in schools) {
            val schoolItem = NycListItem.SchoolItemUiModel(
                borough = school.borough,
                school = school
            )
            schoolListItemUiModels.add(schoolItem)
        }
        return schoolListItemUiModels
    }

    private fun onSchoolSelected(schoolItemUiModel: NycListItem.SchoolItemUiModel) {
        if (schoolItemUiModel.isSelected) {
            val nycListItems = getCurrentList()
            val indexOfSchool = nycListItems.indexOf(schoolItemUiModel)
            val indexOfScore = indexOfSchool + 1
            if (indexOfScore <= nycListItems.lastIndex) {
                val newList = getCurrentList().apply {
                    removeAt(indexOfScore)
                    set(indexOfSchool, schoolItemUiModel.copy(isSelected = false, isLoading = false))
                }
                postUpdatedList(newList)
            }
            return
        }

        val disposable =
            getSatScoreDataInteractor.getSatScoreDataByDbn(schoolItemUiModel.school.dbn)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.main())
                .subscribe({ satDataResponse ->
                    processSatScoreResponse(
                        satDataResponse,
                        schoolItemUiModel.school
                    )
                },
                    { throwable -> failedToGetSatData() })

        pendingDownloads[schoolItemUiModel.school.dbn] = disposable

        onDestroyDisposable.add(disposable)
    }

    private fun processSatScoreResponse(satDataResponse: SatDataResponse, school: School) {
        val satScoreData = satDataResponse.satScoreData
        if (!satDataResponse.isSuccessful || satScoreData == null) {
            failedToGetSatData()
            return
        }

        val scoreListItem = NycListItem.SatScoreDataUiModel(
            borough = school.borough,
            satScoreData = satScoreData,
            webPageLink = school.webPageLink
        )

        var targetIndex = 0
        getCurrentList().forEachIndexed { index, schoolListItemUiModel ->
            if (satDataResponse.satScoreData.dbn == (schoolListItemUiModel as? NycListItem.SchoolItemUiModel)?.school?.dbn) {
                targetIndex = index
                return@forEachIndexed
            }
        }

        val newList = getCurrentList().apply {
            set(
                targetIndex, NycListItem.SchoolItemUiModel(
                    borough = school.borough,
                    school = school,
                    isSelected = true,
                    isLoading = false
                )
            )
            add(targetIndex + 1, scoreListItem)
        }

        postUpdatedList(newList)
    }

    private fun failedToGetSatData() {
        postWithError("Failed to load SAT scores")
    }

    private fun getCurrentList(): MutableList<NycListItem> {
        return composeSchoolListUiModelLiveData.value?.schoolListItemUiModels?.toMutableList()
            ?: mutableListOf()
    }

}
