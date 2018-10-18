package ru.pyrovsergey.gallerya3.model.pojo;

import java.util.Objects;

public class Album {
    private int id;
    private int userId;
    private String title;

    public Album(int id, int userId, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
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
