package com.app.ascentsparkmachinetest.userDetailsMvp;

import com.app.ascentsparkmachinetest.model.UserDetailsData;
import java.util.List;

public interface DetailListContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<UserDetailsData> userArrayList);

            void onFailure(Throwable t);
            void onSuccessFalse(Boolean success);
        }

        void getUserList(OnFinishedListener onFinishedListener);

    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<UserDetailsData> userArrayList);

        void onResponseFailure(Throwable throwable);
        void showMessage();

    }

    interface Presenter {

        void onDestroy();
        void requestDataFromServer();


    }
}