package ru.pyrovsergey.gallerya3.model.repository;

import java.util.ArrayList;
import java.util.List;

import ru.pyrovsergey.gallerya3.model.dto.Album;
import ru.pyrovsergey.gallerya3.model.dto.Photo;
import ru.pyrovsergey.gallerya3.model.dto.User;

public class DataRepository {
    private static DataRepository repository;

    private List<User> repositoryUserList;
    private List<Album> repositoryAlbumList;
    private List<Photo> repositoryPhotoList;

    private DataRepository() {
        repositoryUserList = new ArrayList<>();
        repositoryAlbumList = new ArrayList<>();
        repositoryPhotoList = new ArrayList<>();
    }

    public static synchronized DataRepository getInstance() {
        if (repository == null) {
            repository = new DataRepository();
        }
        return repository;
    }

    public List<User> getRepositoryUserList() {
        return repositoryUserList;
    }

    public void setRepositoryUserList(List<User> repositoryUserList) {
        this.repositoryUserList = repositoryUserList;
    }

    public List<Album> getRepositoryAlbumList() {
        return repositoryAlbumList;
    }

    public void setRepositoryAlbumList(List<Album> repositoryAlbumList) {
        this.repositoryAlbumList = repositoryAlbumList;
    }

    public List<Photo> getRepositoryPhotoList() {
        return repositoryPhotoList;
    }

    public void setRepositoryPhotoList(List<Photo> repositoryPhotoList) {
        this.repositoryPhotoList = repositoryPhotoList;
    }
}
