package ru.pyrovsergey.gallerya3.presenter;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.dto.Album;

public interface AlbumView {
    void startAlbumsLoader(AlbumPresenter albumPresenter);

    void resultLoadAlbumList(List<Album> data);
}
