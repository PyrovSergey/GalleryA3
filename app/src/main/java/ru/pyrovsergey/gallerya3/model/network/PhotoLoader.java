package ru.pyrovsergey.gallerya3.model.network;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.pojo.Photo;

public class PhotoLoader extends AsyncTaskLoader<List<Photo>> {

    private String url;

    public PhotoLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        if (App.checkInternetConnection()) {
            forceLoad();
        }
    }

    @Override
    public List<Photo> loadInBackground() {
        return QueryUtils.getPhotoList(url);
    }
}
