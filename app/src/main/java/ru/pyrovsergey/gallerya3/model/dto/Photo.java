package ru.pyrovsergey.gallerya3.model.dto;

import java.util.Objects;

public class Photo {
    private long albumId;
    private long id;
    private String title;
    private String url;

    public Photo(long albumId, long id, String title, String url) {
        this.albumId = albumId;
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public long getAlbumId() {
        return albumId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return getAlbumId() == photo.getAlbumId() &&
                getId() == photo.getId() &&
                Objects.equals(getTitle(), photo.getTitle()) &&
                Objects.equals(getUrl(), photo.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlbumId(), getId(), getTitle(), getUrl());
    }
}