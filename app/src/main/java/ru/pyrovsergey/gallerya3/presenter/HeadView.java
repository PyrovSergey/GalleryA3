package ru.pyrovsergey.gallerya3.presenter;

import java.util.List;

import ru.pyrovsergey.gallerya3.model.dto.User;

public interface HeadView {
    void startUserLoader();

    void showUsersList(List<User> data);
}

