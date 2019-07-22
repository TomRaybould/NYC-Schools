package com.example.thomasraybould.nycschools.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

public class OpenDbHelper extends SQLiteOpenHelper {

    private static final int VERSION_NUMBER = 1;
    private static final String DB_NAME     = "NYCSchools.db";

    public static final String SCHOOL_TABLE            = "school_table";
    public static final String SCHOOL_DBN              = "school_dbn";
    public static final String SCHOOL_NAME             = "school_name";
    public static final String SCHOOL_BOROUGH          = "school_borough ";
    public static final String SCHOOL_WEB_PAGE_LINK    = "school_web_page_link";

    public static final String SCORE_DATA_TABLE            = "score_data_dbn";
    public static final String SCORE_DATA_DBN              = "score_data_dbn";
    public static final String SCORE_DATA_IS_AVAILABLE     = "score_data_is_available";
    public static final String SCORE_DATA_MATH             = "score_data_math";
    public static final String SCORE_DATA_READING          = "score_data_reading";
    public static final String SCORE_DATA_WRITING          = "score_data_writing";

    @Inject
    public OpenDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION_NUMBER);
    }

    private static final String createSchoolTable =
            "CREATE TABLE "         + SCHOOL_TABLE +" ( "
                    + SCHOOL_DBN            + " VARCHAR UNIQUE,"
                    + SCHOOL_NAME           + " VARCHAR, "
                    + SCHOOL_BOROUGH        + " VARCHAR,"
                    + SCHOOL_WEB_PAGE_LINK  + " VARCHAR "
                    + " ) ";


    private static final String createScoreTable =
            "CREATE TABLE " + SCORE_DATA_TABLE +" ( "
                    + SCORE_DATA_DBN          + " VARCHAR UNIQUE, "
                    + SCORE_DATA_IS_AVAILABLE + " INTEGER, " // 1 for true, 0 for false
                    + SCORE_DATA_MATH         + " INTEGER, "
                    + SCORE_DATA_READING      + " INTEGER, "
                    + SCORE_DATA_WRITING      + " INTEGER"
                    + " ) ";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createSchoolTable);
        sqLiteDatabase.execSQL(createScoreTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
