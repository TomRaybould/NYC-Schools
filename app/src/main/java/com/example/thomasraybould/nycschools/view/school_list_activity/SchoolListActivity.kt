package com.example.thomasraybould.nycschools.view.school_list_activity

import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.OnSchoolListItemSelectedListener
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListAdapter
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SchoolListActivity : AppCompatActivity(), OnSchoolListItemSelectedListener {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var schoolListViewModel : SchoolListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.school_list_activity)

        AndroidInjection.inject(this)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val schoolListAdapter = SchoolListAdapter(this, this, linearLayoutManager)
        recyclerView.adapter = schoolListAdapter

        schoolListViewModel = ViewModelProviders.of(this, factory).get(SchoolListViewModelImpl::class.java)

        schoolListViewModel?.getSchoolList()?.observe(this, Observer { schoolListUiModel ->
            schoolListUiModel?.let {
                schoolListAdapter.updateList(schoolListUiModel.schoolListItemUiModels)
                schoolListUiModel.errorMessage?.let {
                    toast(it)
                }
            }
        })
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel) {
        schoolListViewModel?.onSchoolListItemSelected(schoolListItemUiModel)
    }
}
