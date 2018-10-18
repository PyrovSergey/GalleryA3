package ru.pyrovsergey.gallerya3.model.network.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import ru.pyrovsergey.gallerya3.R;
import ru.pyrovsergey.gallerya3.app.App;

public final class ConnectionUtils {
    public static boolean checkInternetConnection() {
        if (!isInternetAvailable()) {
            Toast.makeText(App.getInstance(),
                    App.getInstance().getString(R.string.no_internet_connection) + "\n" +
                            App.getInstance().getString(R.string.check_connection_settings), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private static boolean isInternetAvailable() {
        ConnectivityManager manager = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isAvailable() &&
                manager.getActiveNetworkInfo().isConnected();
    }
}
