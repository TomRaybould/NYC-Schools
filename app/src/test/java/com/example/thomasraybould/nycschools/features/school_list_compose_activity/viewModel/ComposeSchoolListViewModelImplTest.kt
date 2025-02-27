package com.example.thomasraybould.nycschools.features.school_list_compose_activity.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.thomasraybould.nycschools.CommonMocks
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.School
import com.example.thomasraybould.nycschools.features.uiModels.NycListItem
import com.example.thomasraybould.nycschools.rx_util.SchedulerProvider
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
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
        setupGetSchoolListInteractor(Borough.MANHATTAN)
    }

    @Test
    fun `given the screen is initialized successfully then the user should be displayed a list of boroughs`() {
        val expected = listOf(
            MANHATTAN_BOROUGH_ITEM,
            BROOKLYN_BOROUGH_ITEM,
            QUEEN_BOROUGH_ITEM,
            STATEN_ISLAND_BOROUGH_ITEM,
            BRONX_BOROUGH_ITEM,
        )
        val viewModel = getViewModel()
        val schoolList = viewModel.getSchoolList().value?.schoolListItemUiModels

        Assert.assertEquals(expected, schoolList)
    }


    @Test
    fun `given the user clicks on one of the boroughs then the schools for that borough should be added to the list`() {
        val expected = listOf(
            MANHATTAN_BOROUGH_ITEM.copy(isSelected = true),
            MANHATTAN_SCHOOL_LIST_ITEM_1,
            MANHATTAN_SCHOOL_LIST_ITEM_2,
            MANHATTAN_SCHOOL_LIST_ITEM_3,
            BROOKLYN_BOROUGH_ITEM,
            QUEEN_BOROUGH_ITEM,
            STATEN_ISLAND_BOROUGH_ITEM,
            BRONX_BOROUGH_ITEM,
        )
        val viewModel = getViewModel()
        val schoolListItemUiModels = viewModel.getSchoolList().value?.schoolListItemUiModels

        val manhattan = schoolListItemUiModels!!.first { it.borough == Borough.MANHATTAN }

        viewModel.onSchoolListItemSelected(manhattan)

        val schoolList = viewModel.getSchoolList().value?.schoolListItemUiModels

        Assert.assertEquals(expected, schoolList)
    }

    private fun getViewModel(): ComposeSchoolListViewModel {
        return ComposeSchoolListViewModelImpl(
            getSchoolListInteractor,
            getSatScoreDataInteractor,
            schedulerProvider
        )
    }

    private fun setupGetSchoolListInteractor(
        borough: Borough, schools: List<School> = MANHATTAN_SCHOOLS
    ) {
        every { getSchoolListInteractor.getSchoolsByBorough(borough) } returns Single.just(
            SchoolListResponse.success(schools, borough)
        )
    }

    companion object {

        val TEST_SCHOOL_1 = School.newBuilder()
            .dbn("326423")
            .name("test school 1")
            .borough(Borough.MANHATTAN)
            .webpageLink("www.test1.com")
            .build()

        val TEST_SCHOOL_2 = School.newBuilder()
            .dbn("65437")
            .name("test school 1")
            .borough(Borough.MANHATTAN)
            .webpageLink("www.test1.com")
            .build()

        val TEST_SCHOOL_3 = School.newBuilder()
            .dbn("236547")
            .name("test school 1")
            .borough(Borough.MANHATTAN)
            .webpageLink("www.test1.com")
            .build()

        val MANHATTAN_SCHOOLS = listOf(
            TEST_SCHOOL_1,
            TEST_SCHOOL_2,
            TEST_SCHOOL_3
        )

        val MANHATTAN_SCHOOL_LIST_ITEM_1 = NycListItem.SchoolItemUiModel(
            borough = TEST_SCHOOL_1.borough,
            school = TEST_SCHOOL_1
        )

        val MANHATTAN_SCHOOL_LIST_ITEM_2 = NycListItem.SchoolItemUiModel(
            borough = TEST_SCHOOL_2.borough,
            school = TEST_SCHOOL_2
        )

        val MANHATTAN_SCHOOL_LIST_ITEM_3 = NycListItem.SchoolItemUiModel(
            borough = TEST_SCHOOL_3.borough,
            school = TEST_SCHOOL_3
        )

        val MANHATTAN_BOROUGH_ITEM = NycListItem.BoroughItemUiModel(
            Borough.MANHATTAN,
            isLoading = false,
            isSelected = false
        )
        val BROOKLYN_BOROUGH_ITEM = NycListItem.BoroughItemUiModel(
            Borough.BROOKLYN,
            isLoading = false,
            isSelected = false
        )
        val QUEEN_BOROUGH_ITEM = NycListItem.BoroughItemUiModel(
            Borough.QUEENS,
            isLoading = false,
            isSelected = false
        )
        val STATEN_ISLAND_BOROUGH_ITEM = NycListItem.BoroughItemUiModel(
            Borough.STATEN_ISLAND,
            isLoading = false,
            isSelected = false
        )
        val BRONX_BOROUGH_ITEM = NycListItem.BoroughItemUiModel(
            Borough.BRONX,
            isLoading = false,
            isSelected = false
        )
    }

}