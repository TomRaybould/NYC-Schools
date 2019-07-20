package com.example.thomasraybould.nycschools.adapters.school_list_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.entities.School;

import java.util.List;

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.ViewHolder>{

    private final List<School> schools;
    private final Context context;

    private SchoolListAdapter(Context context, List<School> schools) {
        this.context = context;
        this.schools = schools;
    }

    public static SchoolListAdapter createSchoolListAdapter(Context context, List<School> schools) {
        return new SchoolListAdapter(context, schools);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.school_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        School school = schools.get(position);
        holder.bindSchool(school);
    }

    @Override
    public int getItemCount() {
        return schools.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.schoolNameTextView);
        }

        void bindSchool(School school){
            textView.setText(school.getName());
        }
    }

}
