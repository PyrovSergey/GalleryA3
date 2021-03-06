package ru.pyrovsergey.gallerya3.model.network.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import ru.pyrovsergey.gallerya3.model.pojo.Album;
import ru.pyrovsergey.gallerya3.model.pojo.Photo;
import ru.pyrovsergey.gallerya3.model.pojo.User;

public final class QueryUtils {
    private static final String USER_ID = "userId";
    private static final String GET = "GET";
    private static final String ALBUM_ID = "albumId";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final String NAME = "name";
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    private static final int OK = 200;

    public static List<Photo> getPhotoList(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractPhotoListFromJson(jsonResponse);
    }

    private static List<Photo> extractPhotoListFromJson(String requestJSON) {
        if (TextUtils.isEmpty(requestJSON)) {
            return null;
        }
        List<Photo> photos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(requestJSON);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentPhoto = jsonArray.optJSONObject(i);
                if (currentPhoto == null) {
                    continue;
                }
                int albumId = currentPhoto.optInt(ALBUM_ID);
                int id = currentPhoto.optInt(ID);
                String title = currentPhoto.optString(TITLE);
                String url = currentPhoto.optString(URL);
                photos.add(new Photo(albumId, id, title, url));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photos;
    }

    public static List<User> getUsersList(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractUsersListFromJson(jsonResponse);
    }

    private static List<User> extractUsersListFromJson(String requestJSON) {
        if (TextUtils.isEmpty(requestJSON)) {
            return null;
        }
        List<User> users = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(requestJSON);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentUser = jsonArray.optJSONObject(i);
                if (currentUser == null) {
                    continue;
                }
                int id = currentUser.optInt(ID);
                String name = currentUser.optString(NAME);
                users.add(new User(id, name));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<Album> getUserAlbumsList(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractUserAlbumsListFromJson(jsonResponse);
    }

    private static List<Album> extractUserAlbumsListFromJson(String requestJSON) {
        if (TextUtils.isEmpty(requestJSON)) {
            return null;
        }
        List<Album> albums = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(requestJSON);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentAlbum = jsonArray.optJSONObject(i);
                if (currentAlbum == null) {
                    continue;
                }
                int id = currentAlbum.optInt(ID);
                int userId = currentAlbum.optInt(USER_ID);
                String title = currentAlbum.optString(TITLE);
                albums.add(new Album(id, userId, title));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return albums;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setRequestMethod(GET);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
