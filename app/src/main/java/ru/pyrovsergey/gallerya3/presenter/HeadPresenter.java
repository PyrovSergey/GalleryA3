package ru.pyrovsergey.gallerya3.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import java.util.List;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.pojo.User;
import ru.pyrovsergey.gallerya3.model.network.UsersLoader;

public class HeadPresenter implements LoaderManager.LoaderCallbacks<List<User>> {
    private static final int USERS_LOADER_ID = 1;
    private static final String USERS_URL_REQUEST = "https://jsonplaceholder.typicode.com/users";
    private HeadView view;

    public void onAttach(HeadView view) {
        this.view = view;
    }

    public void onDetach() {
        view = null;
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        if (id == USERS_LOADER_ID) {
            return new UsersLoader(App.getInstance().getApplicationContext(), USERS_URL_REQUEST);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        if (data != null) {
            view.showUsersList(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }
}