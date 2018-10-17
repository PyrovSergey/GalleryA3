package ru.pyrovsergey.gallerya3.presenter;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import java.util.List;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.dto.Album;
import ru.pyrovsergey.gallerya3.model.dto.Photo;
import ru.pyrovsergey.gallerya3.model.network.PhotoLoader;
import ru.pyrovsergey.gallerya3.model.repository.DataRepository;

public class PhotoPresenter implements LoaderManager.LoaderCallbacks<List<Photo>> {
    private static final int PHOTOS_LOADER_ID = 3;
    private static final String PHOTOS_URL_REQUEST = "https://jsonplaceholder.typicode.com/albums/"; // ("/photos")
    private static PhotoPresenter photoPresenter;
    private PhotoView view;
    private DataRepository repository;
    private PhotoLoader photoLoader;
    private List<Album> albumList;

    private PhotoPresenter() {
        repository = DataRepository.getInstance();
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
        albumList = data;
        view.startPhotoLoader(this);
    }

    public void transferLoader(PhotoLoader photoLoader) {
        this.photoLoader = photoLoader;
    }

    @Override
    public Loader<List<Photo>> onCreateLoader(int id, Bundle args) {
        if (id == PHOTOS_LOADER_ID) {
            photoLoader = new PhotoLoader(App.getInstance().getApplicationContext(), PHOTOS_URL_REQUEST + albumList.get(0).getId() + "/photos");
        }
        return photoLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Photo>> loader, List<Photo> data) {
        // to do cache
        view.resultLoadPhotoList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Photo>> loader) {

    }

    public void onDetach() {
        view = null;
        this.repository = null;
    }
}
