package ru.pyrovsergey.gallerya3.presenter;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.dto.User;

public class HeadPresenter {
    private static HeadPresenter myHeadPresenter;
    private HeadView view;

    private HeadPresenter() {
    }

    public static synchronized HeadPresenter getPresenter() {
        if (myHeadPresenter == null) {
            myHeadPresenter = new HeadPresenter();
        }
        return myHeadPresenter;
    }

    public void onAttach(HeadView view) {
        this.view = view;
    }

    public void onDetach() {
        view = null;
    }

    public void initUserLoader() {
        view.startUserLoader();
    }

    public void loaderManagerOnLoadFinished(List<User> data) {
        // to do cache method
        view.showUsersList(data);
    }
}