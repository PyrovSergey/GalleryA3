package ru.pyrovsergey.gallerya3.app;

import android.app.Application;

import ru.pyrovsergey.gallerya3.utils.ConnectionUtils;

public class App extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ConnectionUtils.checkInternetConnection();
    }

    public static Application getInstance() {
        return instance;
    }
}