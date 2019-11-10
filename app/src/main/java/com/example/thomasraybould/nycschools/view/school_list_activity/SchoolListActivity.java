package com.example.thomasraybould.nycschools.view.school_list_activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

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
    public void setSchoolList(List<SchoolListItemUiModel> schoolListItemUiModels) {
        schoolListAdapter = SchoolListAdapter.createSchoolListAdapter(this, this, schoolListItemUiModels);
        recyclerView.setAdapter(schoolListAdapter);
    }

    @Override
    public void addItemsForBorough(List<SchoolListItemUiModel> schoolListItemUiModels, Borough borough) {
        int insertTarget = schoolListAdapter.addSchoolItemsForBorough(schoolListItemUiModels, borough);
        linearLayoutManager.scrollToPositionWithOffset(insertTarget - 1, 0);
    }

    @Override
    public void removeItemsForBorough(Borough borough) {
        schoolListAdapter.removeItemsForBorough(borough);
    }

    @Override
    public void addScoreItem(SchoolListItemUiModel scoreItem) {
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

    @Override
    public List<SchoolListItemUiModel> getCurrentListItems() {
        return schoolListAdapter.getCurrentList();
    }

    @Override
    public void onSchoolListItemSelected(SchoolListItemUiModel schoolListItemUiModel) {
        schoolListPresenter.onSchoolListItemSelected(schoolListItemUiModel);
    }
}
