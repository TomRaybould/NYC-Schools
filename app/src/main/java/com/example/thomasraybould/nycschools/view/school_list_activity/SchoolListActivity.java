package com.example.thomasraybould.nycschools.view.school_list_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListAdapter;
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItem;
import com.example.thomasraybould.nycschools.entities.Borough;

import java.util.List;

public class SchoolListActivity extends AppCompatActivity implements SchoolListView {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SchoolListAdapter schoolListAdapter;

    SchoolListPresenter schoolListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_list_activity);

        initViews();

        schoolListPresenter = new SchoolListPresenterImpl();
        schoolListPresenter.onCreate(this);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);

        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
                    @Override protected int getVerticalSnapPreference() {
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
    public void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setSchoolList(List<SchoolListItem> schoolListItems) {
        schoolListAdapter = SchoolListAdapter.createSchoolListAdapter(this, schoolListItems);
        recyclerView.setAdapter(schoolListAdapter);
    }

    @Override
    public void addItemsForBorough(List<SchoolListItem> schoolListItems, Borough borough) {
        int insertTarget = schoolListAdapter.addSchoolItemsForBorough(schoolListItems, borough);
        linearLayoutManager.scrollToPositionWithOffset(insertTarget - 1, 0);
    }

    @Override
    public void removeItemsForBorough(Borough borough) {
        schoolListAdapter.removeItemsForBorough(borough);
    }

    @Override
    public void addScoreItem(SchoolListItem scoreItem) {
        schoolListAdapter.addScoreItemForSchool(scoreItem);
    }

    @Override
    public void removeScoreItem(String schoolDbn) {
        schoolListAdapter.removeScoreItem(schoolDbn);
    }

    @Override
    public void changeBoroughLoadingStatus(Borough borough, boolean isLoading) {
        schoolListAdapter.changeLoadingStatusOfBorough(borough, isLoading);
    }
}
