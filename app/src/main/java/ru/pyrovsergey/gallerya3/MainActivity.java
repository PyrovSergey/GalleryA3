package ru.pyrovsergey.gallerya3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;
import java.util.Objects;

import ru.pyrovsergey.gallerya3.model.network.UsersLoader;
import ru.pyrovsergey.gallerya3.model.pojo.User;
import ru.pyrovsergey.gallerya3.presenter.HeadPresenter;
import ru.pyrovsergey.gallerya3.presenter.HeadView;
import ru.pyrovsergey.gallerya3.model.network.utils.ConnectionUtils;

public class MainActivity extends AppCompatActivity implements HeadView {
    private static final int USERS_LOADER_ID = 1;

    private HeadPresenter presenter;
    private UsersAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new HeadPresenter();
        presenter.onAttach(this);
        progressBar = findViewById(R.id.head_progress_bar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.users);
        RecyclerView recyclerView = findViewById(R.id.users_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new UsersAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ConnectionUtils.checkInternetConnection()) {
            startUserLoader();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private void startUserLoader() {
        getLoaderManager().initLoader(USERS_LOADER_ID, null, presenter);
    }

    @Override
    public void showUsersList(List<User> data) {
        adapter.updateUserList(data);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
