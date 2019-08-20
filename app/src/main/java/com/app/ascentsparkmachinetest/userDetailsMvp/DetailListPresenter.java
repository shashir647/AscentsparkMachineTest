package com.app.ascentsparkmachinetest.userDetailsMvp;

import com.app.ascentsparkmachinetest.model.UserDetailsData;

import java.util.List;

public class DetailListPresenter implements DetailListContract.Presenter, DetailListContract.Model.OnFinishedListener {

    private DetailListContract.View userListView;

    private DetailListContract.Model userListModel;

    public DetailListPresenter(DetailListContract.View userListView) {
        this.userListView = userListView;
        userListModel = new DetailListModel();
    }

    @Override
    public void onDestroy() {
        this.userListView = null;
    }
    
    @Override
    public void requestDataFromServer() {

        if (userListView != null) {
            userListView.showProgress();
        }
        userListModel.getUserList(this);
    }

    @Override
    public void onFinished(List<UserDetailsData> movieArrayList) {
        userListView.setDataToRecyclerView(movieArrayList);
        if (userListView != null) {
            userListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {

        userListView.onResponseFailure(t);
        if (userListView != null) {
            userListView.hideProgress();
        }
    }

    @Override
    public void onSuccessFalse(Boolean success) {
        if (!success){
            if (userListView != null) {
                userListView.hideProgress();
                userListView.showMessage();
            }
        }
    }
}
