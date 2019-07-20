package com.example.thomasraybould.nycschools.network.auth_token_provider;

import java.util.Map;

public interface AuthTokenProvider {

   Map<String, String> getAuthTokenHeaders();

}
