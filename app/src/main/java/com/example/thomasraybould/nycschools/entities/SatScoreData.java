package com.example.thomasraybould.nycschools.entities;

public class SatScoreData {

    private final String dbn;
    private final int math;
    private final int reading;
    private final int writing;

    private SatScoreData(Builder builder) {
        dbn = builder.dbn;
        math = builder.math;
        reading = builder.reading;
        writing = builder.writing;
    }

    public int getWriting() {
        return writing;
    }

    public int getReading() {
        return reading;
    }

    public int getMath() {
        return math;
    }

    public String getDbn() {
        return dbn;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private String dbn;
        private int math;
        private int reading;
        private int writing;

        private Builder() {
        }

        public Builder dbn(String val) {
            dbn = val;
            return this;
        }

        public Builder math(int val) {
            math = val;
            return this;
        }

        public Builder reading(int val) {
            reading = val;
            return this;
        }

        public Builder writing(int val) {
            writing = val;
            return this;
        }

        public SatScoreData build() {
            return new SatScoreData(this);
        }
    }
}
