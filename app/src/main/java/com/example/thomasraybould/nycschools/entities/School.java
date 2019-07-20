package com.example.thomasraybould.nycschools.entities;

public class School {

    private final String dbn;
    private final String name;

    public School(String dbn, String name) {
        this.dbn = dbn;
        this.name = name;
    }

    public String getDbn() {
        return dbn;
    }

    public String getName() {
        return name;
    }

}
