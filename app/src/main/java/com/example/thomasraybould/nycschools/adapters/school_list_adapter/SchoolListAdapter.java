package com.example.thomasraybould.nycschools.adapters.school_list_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.entities.Borough;

import java.util.List;

import static com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType.BOROUGH_TITLE;

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

    public int addItemsForBorough(List<SchoolListItem> newItems, Borough borough){

        //find the title for the borough and add new items underneath
        int insertTarget = - 1;
        for (int i = 0; i < schoolListItems.size(); i++) {
            SchoolListItem schoolListItem = schoolListItems.get(i);
            if(schoolListItem.getType() == BOROUGH_TITLE && schoolListItem.getBorough() == borough){
                insertTarget = i + 1;
            }
        }

        if(insertTarget > - 1) {
            schoolListItems.addAll(insertTarget, newItems);
            notifyItemRangeInserted(insertTarget, newItems.size());
        }

        return insertTarget;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int viewId;
        if(viewType == BOROUGH_TITLE.ordinal()){
            viewId = R.layout.borough_list_item;
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
        if(schoolListItem.getType() == BOROUGH_TITLE){
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

            Runnable onClickRunnable = schoolListItem.getOnClickRunnable();

            if(onClickRunnable != null) {
                itemView.setOnClickListener(view -> onClickRunnable.run());
            }
        }

    }

}
