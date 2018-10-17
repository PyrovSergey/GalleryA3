package ru.pyrovsergey.gallerya3.model.network;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.dto.Photo;

public class PhotoLoader extends AsyncTaskLoader<List<Photo>> {

    private String url;

    public PhotoLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Photo> loadInBackground() {
        return QueryUtils.getPhotoList(url);
    }
}
