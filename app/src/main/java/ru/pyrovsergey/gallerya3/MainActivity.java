package ru.pyrovsergey.gallerya3;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.dto.User;
import ru.pyrovsergey.gallerya3.model.network.UsersLoader;
import ru.pyrovsergey.gallerya3.presenter.HeadPresenter;
import ru.pyrovsergey.gallerya3.presenter.HeadView;

public class MainActivity extends AppCompatActivity implements HeadView, LoaderManager.LoaderCallbacks<List<User>> {
    private static final int USERS_LOADER_ID = 1;
    private static final String USER_URL_REQUEST = "https://jsonplaceholder.typicode.com/users";

    private HeadPresenter presenter;
    private UsersLoader usersLoader;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = HeadPresenter.getPresenter();
        presenter.onAttach(this);
        RecyclerView recyclerView = findViewById(R.id.users_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new UsersAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initUserLoader();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onDetach();
    }

    @Override
    public void startUserLoader() {
        usersLoader = (UsersLoader) getLoaderManager().initLoader(USERS_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        if (id == USERS_LOADER_ID) {
            usersLoader = new UsersLoader(this, USER_URL_REQUEST);
        }
        return usersLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
        presenter.loaderManagerOnLoadFinished(data);
    }

    @Override
    public void showUsersList(List<User> data) {
        adapter.updateUserList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {

    }
}
