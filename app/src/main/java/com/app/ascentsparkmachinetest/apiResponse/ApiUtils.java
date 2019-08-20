package com.app.ascentsparkmachinetest.apiResponse;

public class ApiUtils {

    private ApiUtils() {}

    private static final String BASE_URL = "https://webappfactory.co/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
