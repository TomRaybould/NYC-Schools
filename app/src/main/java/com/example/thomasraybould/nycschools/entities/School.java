package com.example.thomasraybould.nycschools.entities;

public class School {

    private final String dbn;
    private final String name;
    private final Borough borough;
    private final String webPageLink;


    private School(Builder builder) {
        dbn = builder.dbn;
        name = builder.name;
        borough = builder.borough;
        webPageLink = builder.webPageLink;
    }

    public static Builder newBuilder() {
        return new Builder();
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

    public String getWebPageLink() {
        return webPageLink;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof School)){
            return false;
        }
        return ((School) obj).dbn.equals(this.dbn);
    }

    public static final class Builder {
        private String dbn;
        private String name;
        private Borough borough;
        private String webPageLink;

        private Builder() {
        }

        public Builder dbn(String val) {
            dbn = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder borough(Borough val) {
            borough = val;
            return this;
        }

        public Builder webpageLink(String val) {
            webPageLink = val;
            return this;
        }

        public School build() {
            return new School(this);
        }
    }
}
