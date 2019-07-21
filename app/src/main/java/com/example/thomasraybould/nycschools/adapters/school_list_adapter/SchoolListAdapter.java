package com.example.thomasraybould.nycschools.adapters.school_list_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.entities.Borough;
import com.example.thomasraybould.nycschools.entities.SatScoreData;
import com.example.thomasraybould.nycschools.entities.School;
import com.example.thomasraybould.nycschools.util.StringUtil;

import java.util.List;

import static com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType.BOROUGH_TITLE;
import static com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType.SAT_SCORE_ITEM;
import static com.example.thomasraybould.nycschools.adapters.school_list_adapter.SchoolListItemType.SCHOOL_ITEM;

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

    public int addSchoolItemsForBorough(List<SchoolListItem> newItems, Borough borough){

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


    /**
     * Searching for the target school that was selected and then
     * adding a score item underneath and then returning the position
     * of the school.
     * @param scoreItem
     * @return
     */
    public int addScoreItemForSchool(SchoolListItem scoreItem){
        String targetDbn = scoreItem.getSchool().getDbn();

        int insertTarget = - 1;
        for (int i = 0; i < schoolListItems.size(); i++) {
            SchoolListItem schoolListItem = schoolListItems.get(i);
            if(schoolListItem.getType() != SCHOOL_ITEM){
                continue;
            }
            if(targetDbn.equals(schoolListItem.getSchool().getDbn())){
                insertTarget = i + 1;
            }
        }

        if(insertTarget > - 1) {
            schoolListItems.add(insertTarget, scoreItem);
            notifyItemInserted(insertTarget);
        }

        return insertTarget;
    }

    public void removeScoreItem(String targetDbn){

        for (int i = 0; i < schoolListItems.size(); i++) {
            SchoolListItem schoolListItem = schoolListItems.get(i);
            if(schoolListItem.getType() != SAT_SCORE_ITEM){
                continue;
            }
            if(targetDbn.equals(schoolListItem.getSchool().getDbn())){
                schoolListItems.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }


    }


    public void removeItemsForBorough(Borough borough) {
        //go through list in reverse to remove items
        for (int i = schoolListItems.size() - 1; i >= 0; i--) {
            SchoolListItem schoolListItem = schoolListItems.get(i);

            if(schoolListItem.getType() != BOROUGH_TITLE && schoolListItem.getBorough() == borough){
                schoolListItems.remove(i);
                notifyItemRemoved(i);
            }

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int viewId;
        if(viewType == BOROUGH_TITLE.ordinal()){
            viewId = R.layout.borough_list_item;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SchoolListItem schoolListItem = schoolListItems.get(position);
        if(schoolListItem.getType() == BOROUGH_TITLE){
            holder.bindBorough(schoolListItem, context);
        }
        else if(schoolListItem.getType() == SAT_SCORE_ITEM){
            holder.bindScore(schoolListItem);
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
        ImageView imageView;

        TextView mathScoreTextView, readingScoreTextView, writingScoreTextView;
        ProgressBar mathScoreProgressBar, readingScoreProgressBar, writingScoreProgressBar;
        TextView webPageLinkTextView;

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

        }

        void bindSchool(SchoolListItem schoolListItem){
            textView.setText(schoolListItem.getSchool().getName());
            Runnable onClickRunnable = schoolListItem.getOnClickRunnable();
            if(onClickRunnable != null) {
                textView.setOnClickListener(view -> onClickRunnable.run());
            }
        }

        void bindBorough(SchoolListItem schoolListItem, Context context){
            textView.setText(schoolListItem.getBorough().boroughTitle);

            Glide.with(context)
                    .applyDefaultRequestOptions(RequestOptions.centerCropTransform())
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(schoolListItem.getImageResId())
                    .into(imageView);


            Runnable onClickRunnable = schoolListItem.getOnClickRunnable();

            if(onClickRunnable != null) {
                itemView.setOnClickListener(view -> onClickRunnable.run());
            }
        }

        void bindScore(SchoolListItem schoolListItem) {
            SatScoreData satScoreData = schoolListItem.getSatScoreData();

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

            int mathPercent    = (int)(100 * satScoreData.getMath() / 600f);
            int readingPercent = (int)(100 * satScoreData.getReading() / 600f);
            int writingPercent = (int)(100 * satScoreData.getWriting() / 600f);

            mathScoreProgressBar.setProgress(mathPercent);
            readingScoreProgressBar.setProgress(readingPercent);
            writingScoreProgressBar.setProgress(writingPercent);

            String webPageLink = schoolListItem.getSchool().getWebPageLink();
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
            return "<b><font color='black'>"+string+"</font></b>/600";
        }

    }

}
