package com.example.thomasraybould.nycschools.adapters.school_list_adapter;

import com.example.thomasraybould.nycschools.entities.Borough;

public class SchoolListItem {

    private final SchoolListItemType type;
    private final Borough borough;
    private final String titleText;


    private SchoolListItem(SchoolListItemType type, Borough borough, String titleText) {
        this.type = type;
        this.borough = borough;
        this.titleText = titleText;
    }

    public String getTitleText() {
        return titleText;
    }

    public SchoolListItemType getType() {
        return type;
    }

    public Borough getBorough() {
        return borough;
    }

    public static SchoolListItem createSchoolListItem(SchoolListItemType type, Borough borough, String titleText) {
        return new SchoolListItem(type, borough, titleText);
    }

    public static SchoolListItem createSchoolItem(String titleText) {
        return new SchoolListItem(SchoolListItemType.SCHOOL_ITEM, null, titleText);
    }

    public static SchoolListItem createBoroughItem(String titleText, Borough borough) {
        return new SchoolListItem(SchoolListItemType.BOROUGH_TITLE, borough, titleText);
    }

}
