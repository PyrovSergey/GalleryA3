package ru.pyrovsergey.gallerya3;

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

public class MainActivity extends AppCompatActivity implements HeadView {
    private static final int USERS_LOADER_ID = 1;

    private HeadPresenter presenter;
    private UsersLoader usersLoader;
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = HeadPresenter.getPresenter();
        RecyclerView recyclerView = findViewById(R.id.users_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new UsersAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onAttach(this);
        presenter.initUserLoader();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void startUserLoader(HeadPresenter headPresenter) {
        usersLoader = (UsersLoader) getLoaderManager().initLoader(USERS_LOADER_ID, null, headPresenter);
        presenter.createdLoader(usersLoader);
    }

    @Override
    public void showUsersList(List<User> data) {
        adapter.updateUserList(data);
    }
}
