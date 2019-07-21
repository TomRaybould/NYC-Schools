package com.example.thomasraybould.nycschools.entities;

public enum Borough {

    MANHATTAN("Manhattan"),
    BROOKLYN("Brooklyn"),
    QUEENS("Queens"),
    BRONX("Bronx"),
    STATEN_ISLAND("Staten Island");

    public final String boroughTitle;


    Borough(String boroughTitle) {
        this.boroughTitle = boroughTitle;
    }
}



