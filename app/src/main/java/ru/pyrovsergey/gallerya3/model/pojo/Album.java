package ru.pyrovsergey.gallerya3.model.pojo;

import java.util.Objects;

public class Album {
    private long userId;
    private long id;
    private String title;

    public Album(long userId, long id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    public long getUserId() {
        return userId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return getUserId() == album.getUserId() &&
                getId() == album.getId() &&
                Objects.equals(getTitle(), album.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getId(), getTitle());
    }
}
