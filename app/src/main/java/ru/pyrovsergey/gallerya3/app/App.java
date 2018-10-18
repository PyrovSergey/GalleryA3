package ru.pyrovsergey.gallerya3.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import ru.pyrovsergey.gallerya3.R;

public class App extends Application {
    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        checkInternetConnection();
    }

    public static Application getInstance() {
        return instance;
    }

    public static boolean checkInternetConnection() {
        if (!isInternetAvailable()) {
            Toast.makeText(getInstance().getApplicationContext(),
                    instance.getString(R.string.no_internet_connection) + "\n" +
                            instance.getString(R.string.check_connection_settings), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private static boolean isInternetAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isAvailable() &&
                manager.getActiveNetworkInfo().isConnected();
    }
}