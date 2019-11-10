package com.example.thomasraybould.nycschools.adapters.school_list_adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.databinding.BoroughListItemBinding;
import com.example.thomasraybould.nycschools.entities.Borough;
import com.example.thomasraybould.nycschools.entities.SatScoreData;
import com.example.thomasraybould.nycschools.entities.School;
import com.example.thomasraybould.nycschools.util.StringUtil;

import java.util.List;

import static com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType.BOROUGH_TITLE;
import static com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType.SAT_SCORE_ITEM;
import static com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType.SCHOOL_ITEM;

public class SchoolListAdapter extends RecyclerView.Adapter<SchoolListAdapter.ViewHolder>{

    private final OnSchoolListItemSelectedListener listener;
    private final List<SchoolListItemUiModel> schoolListItemUiModels;
    private final Context context;

    private enum LoadingPayLoad{
        LOADING_PAY_LOAD
    }

    private SchoolListAdapter(OnSchoolListItemSelectedListener listener, Context context, List<SchoolListItemUiModel> schoolListItemUiModels) {
        this.listener = listener;
        this.schoolListItemUiModels = schoolListItemUiModels;
        this.context = context;
    }

    public static SchoolListAdapter createSchoolListAdapter(OnSchoolListItemSelectedListener listener, Context context, List<SchoolListItemUiModel> schoolListItemUiModels) {
        return new SchoolListAdapter(listener, context, schoolListItemUiModels);
    }

    public int addSchoolItemsForBorough(List<SchoolListItemUiModel> newItems, Borough borough){

        //find the title for the borough and add new items underneath
        int insertTarget = - 1;
        for (int i = 0; i < schoolListItemUiModels.size(); i++) {
            SchoolListItemUiModel schoolListItemUiModel = schoolListItemUiModels.get(i);
            if(schoolListItemUiModel.getType() == BOROUGH_TITLE && schoolListItemUiModel.getBorough() == borough){
                schoolListItemUiModel.setSelected(true);
                insertTarget = i + 1;
            }
        }

        if(insertTarget > - 1) {
            schoolListItemUiModels.addAll(insertTarget, newItems);
            notifyItemRangeInserted(insertTarget, newItems.size());
        }

        return insertTarget;
    }


    /**
     * Searching for the target school that was selected and then
     * adding a score item underneath and then returning the position
     * of the school.
     * @param scoreItem
     * @return
     */
    public int addScoreItemForSchool(SchoolListItemUiModel scoreItem){
        String targetDbn = scoreItem.getSchool().getDbn();

        int insertTarget = - 1;
        for (int i = 0; i < schoolListItemUiModels.size(); i++) {
            SchoolListItemUiModel schoolListItemUiModel = schoolListItemUiModels.get(i);
            if(schoolListItemUiModel.getType() != SCHOOL_ITEM){
                continue;
            }
            if(targetDbn.equals(schoolListItemUiModel.getSchool().getDbn())){
                insertTarget = i + 1;
            }
        }

        if(insertTarget > - 1) {
            schoolListItemUiModels.add(insertTarget, scoreItem);
            notifyItemInserted(insertTarget);
        }

        return insertTarget;
    }

    public void removeScoreItem(String targetDbn){

        for (int i = 0; i < schoolListItemUiModels.size(); i++) {
            SchoolListItemUiModel schoolListItemUiModel = schoolListItemUiModels.get(i);
            School school = schoolListItemUiModel.getSchool();

            String schoolItemDbn = null;

            schoolItemDbn = school != null ? school.getDbn() : null;

            if(targetDbn.equals(schoolItemDbn)) {
                if (schoolListItemUiModel.getType() == SAT_SCORE_ITEM) {
                    schoolListItemUiModels.remove(i);
                    notifyItemRemoved(i);
                    break;
                }

            }
        }

    }

    public void changeLoadingStatusOfBorough(Borough borough, boolean isLoading){
        for(int i = 0; i < schoolListItemUiModels.size(); i++){
            SchoolListItemUiModel schoolListItemUiModel = schoolListItemUiModels.get(i);
            if(schoolListItemUiModel.getType() == BOROUGH_TITLE && schoolListItemUiModel.getBorough() == borough){
                schoolListItemUiModel.setLoading(isLoading);
                notifyItemChanged(i, LoadingPayLoad.LOADING_PAY_LOAD);
                break;
            }
        }
    }

    public void removeItemsForBorough(Borough borough) {
        //go through list in reverse to remove items
        for (int i = schoolListItemUiModels.size() - 1; i >= 0; i--) {
            SchoolListItemUiModel schoolListItemUiModel = schoolListItemUiModels.get(i);

            if(schoolListItemUiModel.getType() != BOROUGH_TITLE && schoolListItemUiModel.getBorough() == borough){
                schoolListItemUiModels.remove(i);
                notifyItemRemoved(i);
            }

        }
    }

    public List<SchoolListItemUiModel> getCurrentList() {
        return schoolListItemUiModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final int viewId;
        if(viewType == BOROUGH_TITLE.ordinal()){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            BoroughListItemBinding itemBinding = BoroughListItemBinding.inflate(layoutInflater, parent, false);
            return new ViewHolder(itemBinding);
        }
        else if(viewType == SAT_SCORE_ITEM.ordinal()){
            viewId = R.layout.sat_score_list_item;
        }
        else{
            viewId = R.layout.school_list_item;
        }

        View view = LayoutInflater.from(context).inflate(viewId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(!payloads.isEmpty() && payloads.get(0) == LoadingPayLoad.LOADING_PAY_LOAD){
            SchoolListItemUiModel schoolListItemUiModel = schoolListItemUiModels.get(position);
            if(schoolListItemUiModel.getType() == BOROUGH_TITLE){
                holder.boroughListItemBinding.setUiModel(schoolListItemUiModel);
            }
            return;
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SchoolListItemUiModel schoolListItemUiModel = schoolListItemUiModels.get(position);
        if(schoolListItemUiModel.getType() == BOROUGH_TITLE){
            holder.bindBorough(schoolListItemUiModel, context, listener);
        }
        else if(schoolListItemUiModel.getType() == SAT_SCORE_ITEM){
            holder.bindScore(schoolListItemUiModel, context);
        }
        else{
            holder.bindSchool(schoolListItemUiModel, listener);
        }
    }

    @Override
    public int getItemCount() {
        return schoolListItemUiModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return schoolListItemUiModels.get(position).getType().ordinal();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        BoroughListItemBinding boroughListItemBinding;
        TextView textView;
        ImageView imageView;
        ProgressBar boroughLoadingProgressBar;

        TextView mathScoreTextView, readingScoreTextView, writingScoreTextView;
        ProgressBar mathScoreProgressBar, readingScoreProgressBar, writingScoreProgressBar;
        TextView webPageLinkTextView;

        ViewHolder(BoroughListItemBinding boroughListItemBinding){
            super(boroughListItemBinding.getRoot());
            this.boroughListItemBinding = boroughListItemBinding;
        }

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.schoolNameTextView);
            imageView = itemView.findViewById(R.id.imageView);
            mathScoreTextView       = itemView.findViewById(R.id.mathScoreTextView);
            readingScoreTextView    = itemView.findViewById(R.id.readingScoreTextView);
            writingScoreTextView    = itemView.findViewById(R.id.writingScoreTextView);
            mathScoreProgressBar    = itemView.findViewById(R.id.mathProgressBar);
            readingScoreProgressBar    = itemView.findViewById(R.id.readingProgressBar);
            writingScoreProgressBar    = itemView.findViewById(R.id.writingProgressBar);
            webPageLinkTextView        = itemView.findViewById(R.id.webPageTextView);
            boroughLoadingProgressBar  = itemView.findViewById(R.id.progressBar);
        }

        void bindSchool(SchoolListItemUiModel schoolListItemUiModel, OnSchoolListItemSelectedListener listener){
            textView.setText(schoolListItemUiModel.getSchool().getName());
            itemView.setOnClickListener((v)->{
                listener.onSchoolListItemSelected(schoolListItemUiModel);
                schoolListItemUiModel.setSelected(!schoolListItemUiModel.isSelected());
            });
        }

        void bindBorough(SchoolListItemUiModel schoolListItemUiModel, Context context, OnSchoolListItemSelectedListener listener){
            boroughListItemBinding.setUiModel(schoolListItemUiModel);

            boroughListItemBinding.getRoot().setOnClickListener((v)->{
                listener.onSchoolListItemSelected(schoolListItemUiModel);
                schoolListItemUiModel.setSelected(!schoolListItemUiModel.isSelected());
            });

            boroughListItemBinding.progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.progress_bar_color), PorterDuff.Mode.SRC_IN);
        }

        void bindScore(SchoolListItemUiModel schoolListItemUiModel, Context context) {
            SatScoreData satScoreData = schoolListItemUiModel.getSatScoreData();

            if(satScoreData.isDataAvailable()) {
                Spanned mathSpanned = Html.fromHtml(addBlackStyleToString(satScoreData.getMath() + ""));
                mathScoreTextView.setText(mathSpanned);

                Spanned readingSpanned = Html.fromHtml(addBlackStyleToString(satScoreData.getReading()+ ""));
                readingScoreTextView.setText(readingSpanned);

                Spanned writingSpanned = Html.fromHtml(addBlackStyleToString(satScoreData.getWriting() + ""));
                writingScoreTextView.setText(writingSpanned);
            }
            else{
                mathScoreTextView.setText("N/A");
                readingScoreTextView.setText("N/A");
                writingScoreTextView.setText("N/A");
            }

            int mathPercent    = (int)(100 * satScoreData.getMath() / 800f);
            int readingPercent = (int)(100 * satScoreData.getReading() / 800f);
            int writingPercent = (int)(100 * satScoreData.getWriting() / 800f);

            setProgressBarColorDeterminate(mathScoreProgressBar, context);
            setProgressBarColorDeterminate(readingScoreProgressBar, context);
            setProgressBarColorDeterminate(writingScoreProgressBar, context);

            mathScoreProgressBar.setProgress(mathPercent);
            readingScoreProgressBar.setProgress(readingPercent);
            writingScoreProgressBar.setProgress(writingPercent);

            String webPageLink = schoolListItemUiModel.getSchool().getWebPageLink();
            if(StringUtil.isStringValid(webPageLink)){

                if(!webPageLink.contains("http")) {
                    webPageLink = "http://" + webPageLink;
                }

                String text = "<a href='" + webPageLink + "'>Visit Website</a>";
                webPageLinkTextView.setText(Html.fromHtml(text));
                webPageLinkTextView.setVisibility(View.VISIBLE);
                webPageLinkTextView.setMovementMethod(LinkMovementMethod.getInstance());
            }
            else{
                webPageLinkTextView.setVisibility(View.GONE);
            }
        }

        private static String addBlackStyleToString(String string){
            return "<b><font color='black'>"+string+"</font></b>/800";
        }

        private static void setProgressBarColorDeterminate(ProgressBar progressBar, Context context){
            progressBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(context, R.color.progress_bar_color), PorterDuff.Mode.SRC_IN);
        }

    }

}
