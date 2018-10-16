package ru.pyrovsergey.gallerya3.model.network;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.dto.User;

public class UsersLoader extends AsyncTaskLoader<List<User>> {

    private String url;

    public UsersLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<User> loadInBackground() {
        List<User> list = QueryUtils.getUsersList(url);
        return list;
    }
}
