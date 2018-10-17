package ru.pyrovsergey.gallerya3.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import java.util.List;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.dto.Album;
import ru.pyrovsergey.gallerya3.model.network.AlbumsLoader;
import ru.pyrovsergey.gallerya3.model.repository.DataRepository;

public class AlbumPresenter implements LoaderManager.LoaderCallbacks<List<Album>> {
    private static final int ALBUMS_LOADER_ID = 2;
    private static final String ALBUMS_URL_REQUEST = "https://jsonplaceholder.typicode.com/users/";
    private static AlbumPresenter albumPresenter;
    private AlbumView view;
    private DataRepository repository;
    private AlbumsLoader albumsLoader;
    private long userId;

    private AlbumPresenter() {
        repository = DataRepository.getInstance();
    }

    public static synchronized AlbumPresenter getPresenter() {
        if (albumPresenter == null) {
            albumPresenter = new AlbumPresenter();
        }
        return albumPresenter;
    }

    public void onAttach(AlbumView view) {
        this.view = view;
    }

    public void initAlbumsLoader(long userId) {
        this.userId = userId;
        view.startAlbumsLoader(this);
    }

    public void transferLoader(AlbumsLoader albumsLoader) {
        this.albumsLoader = albumsLoader;
    }

    @Override
    public Loader<List<Album>> onCreateLoader(int id, Bundle args) {
        if (id == ALBUMS_LOADER_ID) {
            albumsLoader = new AlbumsLoader(App.getInstance().getApplicationContext(), ALBUMS_URL_REQUEST + userId + "/albums");
        }
        return albumsLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Album>> loader, List<Album> data) {
        repository.setRepositoryAlbumList(data);
        view.resultLoadAlbumList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Album>> loader) {

    }

    public void onDetach() {
        view = null;
        //this.repository = null;
    }

}
