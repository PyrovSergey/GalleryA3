package ru.pyrovsergey.gallerya3.presenter;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.pojo.Photo;

public interface PhotoView {
    void startPhotoLoader(PhotoPresenter photoPresenter);

    void resultLoadPhotoList(List<Photo> data);
}
