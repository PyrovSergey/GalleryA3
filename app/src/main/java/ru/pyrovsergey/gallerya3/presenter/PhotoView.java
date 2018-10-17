package ru.pyrovsergey.gallerya3.presenter;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.dto.Photo;

public interface PhotoView {
    void startPhotoLoader(PhotoPresenter photoPresenter);

    void resultLoadPhotoList(List<Photo> data);
}
