package com.example.thomasraybould.nycschools.features.school_list_compose_activity.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.thomasraybould.nycschools.CommonMocks
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.features.uiModels.NycListItem
import com.example.thomasraybould.nycschools.rx_util.SchedulerProvider
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ComposeSchoolListViewModelImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val getSchoolListInteractor = mockk<GetSchoolListInteractor>()
    val getSatScoreDataInteractor = mockk<GetSatScoreDataInteractor>()
    val schedulerProvider = mockk<SchedulerProvider>()

    @Before
    fun setup() {
        CommonMocks.setupScheduleProvider(schedulerProvider)
    }

    @Test
    fun `given the screen is initialized successfully then the user should be displayed a list of boroughs`() {
        val expected = listOf(
            NycListItem.BoroughItemUiModel(
                Borough.MANHATTAN,
                isLoading = false,
                isSelected = false
            ),
            NycListItem.BoroughItemUiModel(
                Borough.BROOKLYN,
                isLoading = false,
                isSelected = false
            ),
            NycListItem.BoroughItemUiModel(
                Borough.QUEENS,
                isLoading = false,
                isSelected = false
            ),
            NycListItem.BoroughItemUiModel(
                Borough.STATEN_ISLAND,
                isLoading = false,
                isSelected = false
            ),
            NycListItem.BoroughItemUiModel(
                Borough.BRONX,
                isLoading = false,
                isSelected = false
            ),
        )
        val viewModel = getViewModel()
        val schoolList = viewModel.getSchoolList().value?.schoolListItemUiModels

        Assert.assertEquals(expected, schoolList)
    }

    fun getViewModel(): ComposeSchoolListViewModel {
        return ComposeSchoolListViewModelImpl(
            getSchoolListInteractor,
            getSatScoreDataInteractor,
            schedulerProvider
        )
    }

}