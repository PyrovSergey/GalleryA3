package ru.pyrovsergey.gallerya3.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import java.util.List;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.network.AlbumsLoader;
import ru.pyrovsergey.gallerya3.model.pojo.Album;

public class AlbumPresenter implements LoaderManager.LoaderCallbacks<List<Album>> {
    private static final int ALBUMS_LOADER_ID = 2;
    private static final String ALBUMS_URL_REQUEST = "https://jsonplaceholder.typicode.com/users/";
    private AlbumView view;
    private AlbumsLoader albumsLoader;
    private long userId;

    public void onAttach(AlbumView view) {
        this.view = view;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Loader<List<Album>> onCreateLoader(int id, Bundle args) {
        if (id == ALBUMS_LOADER_ID) {
            return new AlbumsLoader(App.getInstance().getApplicationContext(), ALBUMS_URL_REQUEST + userId + "/albums");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Album>> loader, List<Album> data) {
        view.resultLoadAlbumList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Album>> loader) {

    }

    public void onDetach() {
        view = null;
    }
}
