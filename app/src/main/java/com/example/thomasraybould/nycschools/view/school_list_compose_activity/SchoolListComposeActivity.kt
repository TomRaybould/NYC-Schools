package com.example.thomasraybould.nycschools.view.school_list_compose_activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.OnNycListItemSelectedListener
import com.example.thomasraybould.nycschools.entities.Borough
import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListViewModel
import com.example.thomasraybould.nycschools.view.school_list_activity.SchoolListViewModelImpl
import com.example.thomasraybould.nycschools.view.uiModels.NycListItem
import dagger.android.AndroidInjection
import javax.inject.Inject

class SchoolListComposeActivity : AppCompatActivity(), OnNycListItemSelectedListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var schoolListViewModel: SchoolListViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        AndroidInjection.inject(this)

        schoolListViewModel =
            ViewModelProvider(this, factory).get(SchoolListViewModelImpl::class.java) ?: return

        schoolListViewModel.getSchoolList().observeForever {
            Log.i("", "")
        }

        setContent {
            SchoolListScreen(schoolListViewModel)
        }

    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onNycListItemSelected(nycListItem: NycListItem) {
        schoolListViewModel?.onSchoolListItemSelected(nycListItem)
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