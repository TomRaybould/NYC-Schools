package com.example.thomasraybould.nycschools.features.school_list_compose_activity.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.thomasraybould.nycschools.CommonMocks
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.GetSatScoreDataInteractor
import com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data.SatDataResponse
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.GetSchoolListInteractor
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.entities.SatScoreData
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
        setupSatScoreDataInteractor()
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

    @Test
    fun `given the user clicks on one of the boroughs 2 time then the schools for that borough should be hidden`() {
        val expected = listOf(
            MANHATTAN_BOROUGH_ITEM.copy(isSelected = false, isLoading = false),
            BROOKLYN_BOROUGH_ITEM,
            QUEEN_BOROUGH_ITEM,
            STATEN_ISLAND_BOROUGH_ITEM,
            BRONX_BOROUGH_ITEM,
        )
        val viewModel = getViewModel()

        var manhattan = viewModel.getListOrThrow().first { it.borough == Borough.MANHATTAN }
        // click manhattan once, show the schools
        viewModel.onSchoolListItemSelected(manhattan)

        manhattan = viewModel.getListOrThrow().first { it.borough == Borough.MANHATTAN }
        // click manhattan twice, hide the schools
        viewModel.onSchoolListItemSelected(manhattan)

        val schoolList = viewModel.getSchoolList().value?.schoolListItemUiModels

        Assert.assertEquals(expected, schoolList)
    }

    @Test
    fun `given the user clicks on one of the boroughs 3 time then the schools for that borough should be added to the list`() {
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

        var manhattan = viewModel.getListOrThrow().first { it.borough == Borough.MANHATTAN }
        // click manhattan once, show the schools
        viewModel.onSchoolListItemSelected(manhattan)

        manhattan = viewModel.getListOrThrow().first { it.borough == Borough.MANHATTAN }
        // click manhattan twice, hide the schools
        viewModel.onSchoolListItemSelected(manhattan)

        manhattan = viewModel.getListOrThrow().first { it.borough == Borough.MANHATTAN }
        // click manhattan three, show the schools
        viewModel.onSchoolListItemSelected(manhattan)

        val schoolList = viewModel.getSchoolList().value?.schoolListItemUiModels

        Assert.assertEquals(expected, schoolList)
    }

    @Test
    fun `given the user clicks on the first school item in the list and the SAT data loads successfully then the expected list should be returned to the user`() {
        val expected = listOf(
            MANHATTAN_BOROUGH_ITEM.copy(isSelected = true),
            MANHATTAN_SCHOOL_LIST_ITEM_1.copy(
                isSelected = true,
                satScoreData = TEST_SAT_DATA_SCHOOL_1
            ),
            MANHATTAN_SCHOOL_LIST_ITEM_2,
            MANHATTAN_SCHOOL_LIST_ITEM_3,
            BROOKLYN_BOROUGH_ITEM,
            QUEEN_BOROUGH_ITEM,
            STATEN_ISLAND_BOROUGH_ITEM,
            BRONX_BOROUGH_ITEM,
        )
        val viewModel = getViewModel()

        setupSatScoreDataInteractor(TEST_SAT_DATA_SCHOOL_1.dbn to TEST_SAT_DATA_SCHOOL_1)

        val manhattan = viewModel.getListOrThrow().first { it.borough == Borough.MANHATTAN }
        // click manhattan once, show the schools
        viewModel.onSchoolListItemSelected(manhattan)

        val firstSchool = viewModel.getListOrThrow()
            .first { (it as? NycListItem.SchoolItemUiModel)?.school?.dbn == TEST_SCHOOL_1.dbn }
        //click on the first school to show scores
        viewModel.onSchoolListItemSelected(firstSchool)

        Assert.assertEquals(expected, viewModel.getListOrThrow())
    }

    @Test
    fun `given the user clicks on the first school item in the list twice and the SAT data loads successfully then the score item should not show`() {
        val expected = listOf(
            MANHATTAN_BOROUGH_ITEM.copy(isSelected = true),
            MANHATTAN_SCHOOL_LIST_ITEM_1.copy(
                isSelected = false, // score is not showing
                satScoreData = TEST_SAT_DATA_SCHOOL_1
            ),
            MANHATTAN_SCHOOL_LIST_ITEM_2,
            MANHATTAN_SCHOOL_LIST_ITEM_3,
            BROOKLYN_BOROUGH_ITEM,
            QUEEN_BOROUGH_ITEM,
            STATEN_ISLAND_BOROUGH_ITEM,
            BRONX_BOROUGH_ITEM,
        )
        val viewModel = getViewModel()

        setupSatScoreDataInteractor(TEST_SAT_DATA_SCHOOL_1.dbn to TEST_SAT_DATA_SCHOOL_1)

        val manhattan = viewModel.getListOrThrow().first { it.borough == Borough.MANHATTAN }
        // click manhattan once, show the schools
        viewModel.onSchoolListItemSelected(manhattan)

        var firstSchool = viewModel.getListOrThrow()
            .first { (it as? NycListItem.SchoolItemUiModel)?.school?.dbn == TEST_SCHOOL_1.dbn }
        //click on the first school to show scores
        viewModel.onSchoolListItemSelected(firstSchool)

        firstSchool = viewModel.getListOrThrow()
            .first { (it as? NycListItem.SchoolItemUiModel)?.school?.dbn == TEST_SCHOOL_1.dbn }
        //click on the first school to show scores
        viewModel.onSchoolListItemSelected(firstSchool)

        Assert.assertEquals(expected, viewModel.getListOrThrow())
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

    private fun setupSatScoreDataInteractor(vararg scores: Pair<String, SatScoreData> = emptyArray()) {
        scores.forEach { scorePair ->
            val dbn = scorePair.first
            val satScoreData = scorePair.second
            every { getSatScoreDataInteractor.getSatScoreDataByDbn(dbn) } returns Single.just(
                SatDataResponse.success(satScoreData)
            )
        }
    }

    private fun ComposeSchoolListViewModel.getListOrThrow(): List<NycListItem> {
        return this.getSchoolList().value!!.schoolListItemUiModels
    }

    companion object {

        val TEST_SCHOOL_1 = School(
            dbn = "326423",
            name = "Test school 1",
            borough = Borough.MANHATTAN,
            webPageLink = "www.test1.com"
        )

        val TEST_SAT_DATA_SCHOOL_1 = SatScoreData(
            dbn = TEST_SCHOOL_1.dbn,
            dataAvailable = true,
            math = 500,
            reading = 200,
            writing = 250
        )

        val TEST_SCHOOL_2 = School(
            dbn = "65437",
            name = "Test school 2",
            borough = Borough.MANHATTAN,
            webPageLink = "www.test2.com"
        )

        val TEST_SCHOOL_3 = School(
            dbn = "236547",
            name = "Test school 3",
            borough = Borough.MANHATTAN,
            webPageLink = "www.test3.com"
        )

        val MANHATTAN_SCHOOLS = listOf(
            TEST_SCHOOL_1,
            TEST_SCHOOL_2,
            TEST_SCHOOL_3
        )

        val MANHATTAN_SCHOOL_LIST_ITEM_1 = NycListItem.SchoolItemUiModel(
            borough = TEST_SCHOOL_1.borough,
            school = TEST_SCHOOL_1,
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