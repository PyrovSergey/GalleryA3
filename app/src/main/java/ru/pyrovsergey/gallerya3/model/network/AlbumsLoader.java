package ru.pyrovsergey.gallerya3.model.network;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.dto.Album;

public class AlbumsLoader extends AsyncTaskLoader<List<Album>> {

    private String url;

    public AlbumsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Album> loadInBackground() {
        return QueryUtils.getUserAlbumsList(url);
    }
}
