package com.example.thomasraybould.nycschools.entities;

public class School {

    private final String dbn;
    private final String name;
    private final Borough borough;

    public School(String dbn, String name, Borough borough) {
        this.dbn = dbn;
        this.name = name;
        this.borough = borough;
    }

    public String getDbn() {
        return dbn;
    }

    public String getName() {
        return name;
    }

    public Borough getBorough() {
        return borough;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof School)){
            return false;
        }
        return ((School) obj).dbn.equals(this.dbn);
    }
}
