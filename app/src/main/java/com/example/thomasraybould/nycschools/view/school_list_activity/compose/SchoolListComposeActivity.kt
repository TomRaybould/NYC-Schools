package com.example.thomasraybould.nycschools.view.school_list_activity.compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.OnSchoolListItemSelectedListener
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListViewModel
import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListViewModelImpl
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem
import dagger.android.AndroidInjection
import javax.inject.Inject

class SchoolListComposeActivity : AppCompatActivity(), OnSchoolListItemSelectedListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var schoolListViewModel: SchoolListViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SchoolListView(listOf())
        }

        AndroidInjection.inject(this)

        schoolListViewModel = ViewModelProvider(this, factory).get(SchoolListViewModelImpl::class.java)

        schoolListViewModel?.getSchoolList()?.observe(this) { schoolListUiModel ->
            schoolListUiModel?.let {
                val nycListItems =
                    schoolListUiModel.schoolListItemUiModels.mapNotNull { schoolListUiModel ->
                        if (schoolListUiModel.type == SchoolListItemType.BOROUGH_TITLE) {
                            NycListItem.BoroughItemUiModel(
                                schoolListUiModel.borough,
                                getImageForBorough(schoolListUiModel.borough),
                                schoolListUiModel.isLoading
                            )
                        } else {
                            null
                        }
                    }
                setContent {
                    SchoolListView(nycListItems)
                }
            }
        }

    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel) {
        schoolListViewModel?.onSchoolListItemSelected(schoolListItemUiModel)
    }

    private fun getImageForBorough(borough: Borough): Int {
        return when (borough.code) {
            "M" -> R.drawable.manhattan
            "K" -> R.drawable.brooklyn
            "Q" -> R.drawable.queens
            "X" -> R.drawable.bronx
            "R" -> R.drawable.statenisland
            else -> R.drawable.manhattan
        }
    }

}