package com.example.thomasraybould.nycschools.view.school_list_activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.OnSchoolListItemSelectedListener;
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListAdapter;
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemUiModel;
import com.example.thomasraybould.nycschools.entities.Borough;

import java.util.List;

public class SchoolListActivity extends AppCompatActivity implements SchoolListView, OnSchoolListItemSelectedListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SchoolListAdapter schoolListAdapter;

    SchoolListPresenter schoolListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_list_activity);

        initViews();

        schoolListAdapter = SchoolListAdapter.Companion.createSchoolListAdapter(SchoolListActivity.this, SchoolListActivity.this);
        recyclerView.setAdapter(schoolListAdapter);

        schoolListPresenter = ViewModelProviders.of(this).get(SchoolListPresenterImpl.class);
        schoolListPresenter.onCreate(this);
        schoolListPresenter.getSchoolList().observe(this, schoolListUiModel -> schoolListAdapter.updateList(schoolListUiModel.getSchoolListItemUiModels()));
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);

        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);

    }


    @Override
    protected void onResume() {
        super.onResume();
        schoolListPresenter.onResume(this);
    }

    @Override
    protected void onPause() {
        schoolListPresenter.onPause();
        super.onPause();
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeBoroughLoadingStatus(Borough borough, boolean isLoading) {
        schoolListAdapter.changeLoadingStatusOfBorough(borough, isLoading);
    }

    @Override
    public List<SchoolListItemUiModel> getCurrentListItems() {
        return schoolListAdapter.getCurrentList();
    }

    @Override
    public void onSchoolListItemSelected(SchoolListItemUiModel schoolListItemUiModel) {
        schoolListPresenter.onSchoolListItemSelected(schoolListItemUiModel);
    }
}
