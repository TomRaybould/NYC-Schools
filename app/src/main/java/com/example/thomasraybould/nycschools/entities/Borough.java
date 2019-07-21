package com.example.thomasraybould.nycschools.entities;

public enum Borough {

    MANHATTAN("M"       ,"Manhattan"),
    BROOKLYN("K"        ,"Brooklyn"),
    QUEENS("Q"          ,"Queens"),
    STATEN_ISLAND("R"   ,"Staten Island"),
    BRONX("X"           ,"Bronx");

    public final String code;
    public final String boroughTitle;


    Borough(String code, String boroughTitle) {
        this.code = code;
        this.boroughTitle = boroughTitle;
    }

    public static Borough fromCode(String boroughCode){

        switch (boroughCode){
            case "M":
                return MANHATTAN;
            case "K":
                return BROOKLYN;
            case "Q":
                return QUEENS;
            case "X":
                return BRONX;
            case "R":
                return STATEN_ISLAND;

            default:
                return null;
        }

    }

}



