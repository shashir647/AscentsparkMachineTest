package com.app.ascentsparkmachinetest.userDetailsMvp;

import android.support.annotation.NonNull;
import android.util.Log;
import com.app.ascentsparkmachinetest.apiResponse.APIService;
import com.app.ascentsparkmachinetest.apiResponse.ApiUtils;
import com.app.ascentsparkmachinetest.model.UserDetailsData;
import com.app.ascentsparkmachinetest.model.UserDataList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailListModel implements DetailListContract.Model {

    private final String TAG = "UserListModel";

    /**
     * This function will fetch user data
     * @param onFinishedListener
     */
    @Override
    public void getUserList(final OnFinishedListener onFinishedListener) {
        APIService mAPIService = ApiUtils.getAPIService();

        mAPIService.getUserDetails("", "").enqueue(new Callback<UserDataList>() {
            @Override
            public void onResponse(@NonNull Call<UserDataList> call, @NonNull Response<UserDataList> response) {

                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getSuccess()){
                        List<UserDetailsData> movies = response.body().getData();
                        onFinishedListener.onFinished(movies);
                    }else {
                        onFinishedListener.onSuccessFalse(response.body().getSuccess());
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<UserDataList> call, @NonNull Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

}
