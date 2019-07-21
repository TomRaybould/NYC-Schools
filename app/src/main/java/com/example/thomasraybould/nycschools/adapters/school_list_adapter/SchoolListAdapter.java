package com.example.thomasraybould.nycschools.adapters.school_list_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thomasraybould.nycschools.R;

import java.util.List;

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.ViewHolder>{

    private final List<SchoolListItem> schoolListItems;
    private final Context context;

    private SchoolListAdapter(Context context, List<SchoolListItem> schoolListItems) {
        this.schoolListItems = schoolListItems;
        this.context = context;
    }

    public static SchoolListAdapter createSchoolListAdapter(Context context, List<SchoolListItem> schoolListItems) {
        return new SchoolListAdapter(context, schoolListItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int viewId;
        if(viewType == SchoolListItemType.BOROUGH_TITLE.ordinal()){
            // TODO: 7/20/19 create a borough layout
            viewId = R.layout.school_list_item;
        }
        else{
            viewId = R.layout.school_list_item;
        }

        View view = LayoutInflater.from(context).inflate(viewId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SchoolListItem schoolListItem = schoolListItems.get(position);
        if(schoolListItem.getType() == SchoolListItemType.BOROUGH_TITLE){
            holder.bindBorough(schoolListItem);
        }
        else{
            holder.bindSchool(schoolListItem);
        }
    }

    @Override
    public int getItemCount() {
        return schoolListItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return schoolListItems.get(position).getType().ordinal();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.schoolNameTextView);
        }

        void bindSchool(SchoolListItem schoolListItem){
            textView.setText(schoolListItem.getTitleText());
        }

        void bindBorough(SchoolListItem schoolListItem){
            textView.setText(schoolListItem.getBorough().boroughTitle);
        }

    }

}