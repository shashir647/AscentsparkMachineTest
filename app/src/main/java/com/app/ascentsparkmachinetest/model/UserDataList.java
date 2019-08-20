package com.app.ascentsparkmachinetest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDataList {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<UserDetailsData> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<UserDetailsData> getData() {
        return data;
    }

    public void setData(List<UserDetailsData> data) {
        this.data = data;
    }

}
