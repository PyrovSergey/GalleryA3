package ru.pyrovsergey.gallerya3.presenter;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.pojo.Album;

public interface AlbumView {
    void startAlbumsLoader(AlbumPresenter albumPresenter);

    void resultLoadAlbumList(List<Album> data);
}
