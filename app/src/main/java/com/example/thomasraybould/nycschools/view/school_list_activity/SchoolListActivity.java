package com.example.thomasraybould.nycschools.view.school_list_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListAdapter;
import com.example.thomasraybould.nycschools.entities.School;

import java.util.List;

public class SchoolListActivity extends AppCompatActivity implements SchoolListView {

    private RecyclerView recyclerView;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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

    private void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSchoolList(List<School> schools) {
        if (!schools.isEmpty()) {

        } else {
            toast("failed to load");
        }
    }
}
