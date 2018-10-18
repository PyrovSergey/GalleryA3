package ru.pyrovsergey.gallerya3.model.network;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.pojo.User;
import ru.pyrovsergey.gallerya3.model.network.utils.ConnectionUtils;
import ru.pyrovsergey.gallerya3.model.network.utils.QueryUtils;

public class UsersLoader extends AsyncTaskLoader<List<User>> {

    private String url;

    public UsersLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        if (ConnectionUtils.checkInternetConnection()) {
            forceLoad();
        }
    }

    @Override
    public List<User> loadInBackground() {
        return QueryUtils.getUsersList(url);
    }
}
