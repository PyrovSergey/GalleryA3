package ru.pyrovsergey.gallerya3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.dto.Album;
import ru.pyrovsergey.gallerya3.model.dto.Photo;
import ru.pyrovsergey.gallerya3.model.network.AlbumsLoader;
import ru.pyrovsergey.gallerya3.model.network.PhotoLoader;
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
    private AlbumsLoader albumsLoader;
    private PhotoLoader photoLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        userId = getIntent().getLongExtra(KEY_ID, 0);
        albumPresenter = AlbumPresenter.getPresenter();
        photoPresenter = PhotoPresenter.getPresenter();
        Toast.makeText(this, String.valueOf(userId), Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView = findViewById(R.id.gallery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PhotoAdapter(userId));
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
        photoPresenter.onAttach(this);
        albumPresenter.onAttach(this);
        albumPresenter.initAlbumsLoader(userId);
    }

    @Override
    protected void onDestroy() {
        albumPresenter.onDetach();
        photoPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void startAlbumsLoader(AlbumPresenter albumPresenter) {
        albumsLoader = (AlbumsLoader) getLoaderManager().initLoader(ALBUMS_LOADER_ID, null, albumPresenter);
        albumPresenter.transferLoader(albumsLoader);
    }

    @Override
    public void startPhotoLoader(PhotoPresenter photoPresenter) {
        photoLoader = (PhotoLoader) getLoaderManager().initLoader(PHOTOS_LOADER_ID, null, photoPresenter);
        photoPresenter.transferLoader(photoLoader);
    }

    @Override
    public void resultLoadAlbumList(List<Album> data) {
//        Log.i("MyTAG", "resultLoadAlbumList()\n");
//        for (int i = 0; i < data.size(); i++) {
//            Log.i("MyTAG", String.valueOf(data.get(i).getTitle()));
//        }
        photoPresenter.initPhotosLoader(data);
    }


    @Override
    public void resultLoadPhotoList(List<Photo> data) {
        Log.i("MyTAG", "resultLoadPhotoList()\n");
        for (int i = 0; i < data.size(); i++) {
            Log.i("MyTAG", String.valueOf(data.get(i).getUrl()));
        }
    }
}
