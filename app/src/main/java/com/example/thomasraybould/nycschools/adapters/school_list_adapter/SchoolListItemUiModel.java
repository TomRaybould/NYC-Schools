package com.example.thomasraybould.nycschools.adapters.school_list_adapter;

import com.example.thomasraybould.nycschools.R;
import com.example.thomasraybould.nycschools.entities.Borough;
import com.example.thomasraybould.nycschools.entities.SatScoreData;
import com.example.thomasraybould.nycschools.entities.School;

public class SchoolListItemUiModel {

    private final SchoolListItemType    type;
    private final SatScoreData          satScoreData;
    private final Borough               borough;
    private final School                school;
    private boolean                     isSelected;
    private final int                   imageResId;
    private boolean                     isLoading;

    private SchoolListItemUiModel(Builder builder) {
        type = builder.type;
        satScoreData = builder.satScoreData;
        borough = builder.borough;
        school = builder.school;
        imageResId = builder.imageResId;
        isLoading = builder.isLoading;
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public School getSchool() {
        return school;
    }

    public SchoolListItemType getType() {
        return type;
    }

    public Borough getBorough() {
        return borough;
    }

    public int getImageResId() {
        return imageResId;
    }

    public SatScoreData getSatScoreData() {
        return satScoreData;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public static SchoolListItemUiModel createSchoolItem(School school, Borough borough) {
        return new Builder()
                .type(SchoolListItemType.SCHOOL_ITEM)
                .school(school)
                .borough(borough)
                .build();
    }

    public static SchoolListItemUiModel createBoroughItem(Borough borough) {

        int imageResId;

        switch (borough.code){
            case "M":
                imageResId = R.drawable.manhattan;
                break;
            case "K":
                imageResId = R.drawable.brooklyn;
                break;
            case "Q":
                imageResId = R.drawable.queens;
                break;
            case "X":
                imageResId = R.drawable.bronx;
                break;
            case "R":
                imageResId = R.drawable.statenisland;
                break;

            default:
                imageResId = R.drawable.manhattan;

        }


        return SchoolListItemUiModel.newBuilder()
                .type(SchoolListItemType.BOROUGH_TITLE)
                .borough(borough)
                .imageResId(imageResId)
                .build();
    }


    public static final class Builder {
        private SchoolListItemType type;
        private SatScoreData satScoreData;
        private Borough borough;
        private School school;
        private int imageResId;
        private boolean isLoading;

        private Builder() {
        }

        public Builder type(SchoolListItemType val) {
            type = val;
            return this;
        }

        public Builder satScoreData(SatScoreData val) {
            satScoreData = val;
            return this;
        }

        public Builder borough(Borough val) {
            borough = val;
            return this;
        }

        public Builder school(School val) {
            school = val;
            return this;
        }

        public Builder imageResId(int val) {
            imageResId = val;
            return this;
        }

        public Builder isLoading(boolean val) {
            isLoading = val;
            return this;
        }

        public SchoolListItemUiModel build() {
            return new SchoolListItemUiModel(this);
        }
    }
}
