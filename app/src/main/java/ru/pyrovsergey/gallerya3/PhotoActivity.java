package ru.pyrovsergey.gallerya3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;
import java.util.Objects;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.network.utils.ConnectionUtils;
import ru.pyrovsergey.gallerya3.model.pojo.Album;
import ru.pyrovsergey.gallerya3.model.pojo.Photo;
import ru.pyrovsergey.gallerya3.presenter.AlbumPresenter;
import ru.pyrovsergey.gallerya3.presenter.AlbumView;
import ru.pyrovsergey.gallerya3.presenter.PhotoPresenter;
import ru.pyrovsergey.gallerya3.presenter.PhotoView;

public class PhotoActivity extends AppCompatActivity implements PhotoView, AlbumView {
    private static final int ALBUMS_LOADER_ID = 2;
    private static final int PHOTOS_LOADER_ID = 3;
    private static final String KEY_ID = "ru.pyrovsergey.gallerya3.key.id";
    private long userId;
    private PhotoPresenter photoPresenter;
    private AlbumPresenter albumPresenter;
    private PhotoAdapter photoAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.photos);
        progressBar = findViewById(R.id.photos_progress_bar);
        userId = getIntent().getLongExtra(KEY_ID, 0);
        albumPresenter = new AlbumPresenter();
        photoPresenter = new PhotoPresenter();
        albumPresenter.onAttach(this);
        photoPresenter.onAttach(this);
        RecyclerView recyclerView = findViewById(R.id.gallery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoAdapter = new PhotoAdapter();
        recyclerView.setAdapter(photoAdapter);
    }

    public static void startDetailActivity(long userId) {
        Context context = App.getInstance().getApplicationContext();
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ConnectionUtils.checkInternetConnection()) {
            albumPresenter.setUserId(userId);
            startAlbumsLoader();
        }
    }

    @Override
    protected void onPause() {
        photoAdapter.stopAllDownloads();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        albumPresenter.onDetach();
        photoPresenter.onDetach();
        super.onDestroy();
    }

    private void startAlbumsLoader() {
        getLoaderManager().initLoader(ALBUMS_LOADER_ID, null, albumPresenter);
    }

    public void startPhotoLoader() {
        getLoaderManager().initLoader(PHOTOS_LOADER_ID, null, photoPresenter);
    }

    @Override
    public void resultLoadAlbumList(List<Album> data) {
        photoPresenter.setData(data);
        startPhotoLoader();
    }

    @Override
    public void resultLoadPhotoList(List<Photo> data) {
        photoAdapter.refreshAdapterPhotoList(data);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
