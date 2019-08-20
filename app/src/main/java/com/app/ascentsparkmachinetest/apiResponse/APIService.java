package com.app.ascentsparkmachinetest.apiResponse;

import com.app.ascentsparkmachinetest.model.UserDataList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @POST("/test/hero.php")
    @FormUrlEncoded
    Call<UserDataList>  getUserDetails(@Field("name") String uname, @Field("currentTime") String uTime);
}
