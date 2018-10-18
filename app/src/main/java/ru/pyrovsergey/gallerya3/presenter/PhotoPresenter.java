package ru.pyrovsergey.gallerya3.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.network.PhotoLoader;
import ru.pyrovsergey.gallerya3.model.pojo.Album;
import ru.pyrovsergey.gallerya3.model.pojo.Photo;

public class PhotoPresenter implements LoaderManager.LoaderCallbacks<List<Photo>> {
    private static final int PHOTOS_LOADER_ID = 3;
    private static final String PHOTOS_URL_REQUEST = "https://jsonplaceholder.typicode.com/albums/";
    private static PhotoPresenter photoPresenter;
    private PhotoView view;

    private PhotoLoader photoLoader;
    private List<Album> albumList;
    private int sizeAlbumList;
    private int currentNumPositionAlbumList = 0;

    private PhotoPresenter() {
        albumList = new ArrayList<>();
    }

    public static synchronized PhotoPresenter getPresenter() {
        if (photoPresenter == null) {
            photoPresenter = new PhotoPresenter();
        }
        return photoPresenter;
    }

    public void onAttach(PhotoView view) {
        this.view = view;
    }

    public void initPhotosLoader(List<Album> data) {
        if (data != null) {
            albumList = data;
            sizeAlbumList = data.size() - 1;
            view.startPhotoLoader(this);
        }
    }

    public void transferLoader(PhotoLoader photoLoader) {
        this.photoLoader = photoLoader;
    }

    @Override
    public Loader<List<Photo>> onCreateLoader(int id, Bundle args) {
        if (id == PHOTOS_LOADER_ID) {
            photoLoader = new PhotoLoader(App.getInstance().getApplicationContext(), PHOTOS_URL_REQUEST + albumList.get(currentNumPositionAlbumList).getId() + "/photos");
        }
        return photoLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Photo>> loader, List<Photo> data) {
        view.resultLoadPhotoList(data);
        if (currentNumPositionAlbumList < sizeAlbumList) {
            photoLoader.setUrl(PHOTOS_URL_REQUEST + albumList.get(++currentNumPositionAlbumList).getId() + "/photos");
            photoLoader.forceLoad();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Photo>> loader) {

    }

    public void onDetach() {
        view = null;
        sizeAlbumList = 0;
        currentNumPositionAlbumList = 0;
    }
}
