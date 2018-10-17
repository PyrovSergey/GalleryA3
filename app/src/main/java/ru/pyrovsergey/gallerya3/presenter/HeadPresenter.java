package ru.pyrovsergey.gallerya3.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import java.util.List;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.dto.User;
import ru.pyrovsergey.gallerya3.model.network.UsersLoader;
import ru.pyrovsergey.gallerya3.model.repository.DataRepository;

public class HeadPresenter implements LoaderManager.LoaderCallbacks<List<User>> {
    private static final int USERS_LOADER_ID = 1;
    private static final String USERS_URL_REQUEST = "https://jsonplaceholder.typicode.com/users";
    private static HeadPresenter myHeadPresenter;
    private DataRepository repository;
    private HeadView view;
    private UsersLoader usersLoader;

    private HeadPresenter() {
        repository = DataRepository.getInstance();
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
        this.repository = null;
    }

    public void initUserLoader() {
        view.startUserLoader(this);
    }

    public void createdLoader(UsersLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        if (id == USERS_LOADER_ID) {
            usersLoader = new UsersLoader(App.getInstance().getApplicationContext(), USERS_URL_REQUEST);
        }
        return usersLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        repository.setRepositoryUserList(data);
        view.showUsersList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }
}