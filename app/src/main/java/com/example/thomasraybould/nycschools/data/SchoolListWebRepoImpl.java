package com.example.thomasraybould.nycschools.data;

import com.example.thomasraybould.nycschools.data.base.AbstractWebRepo;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.SchoolListRepo;
import com.example.thomasraybould.nycschools.domain.get_school_list_interactor.data.SchoolListResponse;
import com.example.thomasraybould.nycschools.entities.School;
import com.example.thomasraybould.nycschools.network.api_url_provider.ApiUrlProvider;
import com.example.thomasraybould.nycschools.network.auth_token_provider.AuthTokenProvider;
import com.example.thomasraybould.nycschools.network.http_client.HttpClient;
import com.example.thomasraybould.nycschools.network.http_client.NetworkRequest;
import com.example.thomasraybould.nycschools.network.http_client.NetworkResponse;
import com.example.thomasraybould.nycschools.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public class SchoolListWebRepoImpl extends AbstractWebRepo implements SchoolListRepo {

    public SchoolListWebRepoImpl(HttpClient httpClient, AuthTokenProvider authTokenProvider, ApiUrlProvider apiUrlProvider) {
        super(httpClient, authTokenProvider, apiUrlProvider);
    }

    @Override
    public Single<SchoolListResponse> getSchools() {
        String schoolListApi = apiUrlProvider.getSchoolListApi();
        Map<String, String> authTokenHeaders = authTokenProvider.getAuthTokenHeaders();

        NetworkRequest networkRequest = NetworkRequest.createNetworkRequest(schoolListApi, authTokenHeaders);

        return httpClient.getJsonArray(networkRequest)
                .map(SchoolListWebRepoImpl::parseSchoolListResponse);
    }

    private static SchoolListResponse parseSchoolListResponse(NetworkResponse<JSONArray> networkResponse){
        JSONArray data = networkResponse.getData();

        if(!networkResponse.isSuccessful() || data == null){
            return SchoolListResponse.failure();
        }

        List<School> schoolList = new ArrayList<>();

        for(int i = 0; i < data.length(); i++){
            JSONObject jsonObject = data.optJSONObject(i);
            if(jsonObject == null){
                continue;
            }

            String dbn = jsonObject.optString("dbn");
            String schoolName = jsonObject.optString("school_name");

            if(!StringUtil.areStringsValid(dbn, schoolName)){
                continue;
            }
            School school = new School(dbn, schoolName);
            schoolList.add(school);
        }

        return SchoolListResponse.success(schoolList);
    }

}
