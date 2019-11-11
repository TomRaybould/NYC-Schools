package com.example.thomasraybould.nycschools.view.school_list_activity

import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

import com.example.thomasraybould.nycschools.R
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.OnSchoolListItemSelectedListener
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListAdapter
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel

class SchoolListActivity : AppCompatActivity(), OnSchoolListItemSelectedListener {

    private var recyclerView: RecyclerView? = null

    private lateinit var schoolListViewModel: SchoolListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.school_list_activity)

        initViews()

        val schoolListAdapter = SchoolListAdapter.createSchoolListAdapter(this@SchoolListActivity, this@SchoolListActivity)
        recyclerView!!.adapter = schoolListAdapter

        schoolListViewModel = ViewModelProviders.of(this).get(SchoolListViewModelImpl::class.java)
        schoolListViewModel.getSchoolList().observe(this, Observer { schoolListViewModel ->
            schoolListViewModel?.let {
                schoolListAdapter.updateList(schoolListViewModel.schoolListItemUiModels)
                schoolListViewModel.errorMessage?.let {
                    toast(it)
                }
            }
        })
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)

        val linearLayoutManager = LinearLayoutManager(this)

        val smoothScroller = object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference(): Int {
                return LinearSmoothScroller.SNAP_TO_START
            }
        }
        recyclerView!!.layoutManager = linearLayoutManager

    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSchoolListItemSelected(schoolListItemUiModel: SchoolListItemUiModel) {
        schoolListViewModel.onSchoolListItemSelected(schoolListItemUiModel)
    }
}
