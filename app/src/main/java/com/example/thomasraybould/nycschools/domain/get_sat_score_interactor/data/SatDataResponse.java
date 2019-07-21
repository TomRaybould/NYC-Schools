package com.example.thomasraybould.nycschools.domain.get_sat_score_interactor.data;

import com.example.thomasraybould.nycschools.entities.SatScoreData;

public class SatDataResponse {

    private final boolean isSuccessful;
    private final SatScoreData satScoreData;

    private SatDataResponse(boolean isSuccessful, SatScoreData satScoreData) {
        this.isSuccessful = isSuccessful;
        this.satScoreData = satScoreData;
    }

    public SatScoreData getSatScoreData() {
        return satScoreData;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public static SatDataResponse failure(){
        return new SatDataResponse(false, SatScoreData.newBuilder().build());
    }

    public static SatDataResponse success(SatScoreData satScoreData){
        return new SatDataResponse(true, satScoreData);
    }

}