package ru.pyrovsergey.gallerya3.model.network;

import android.text.TextUtils;
import android.util.Log;

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

import ru.pyrovsergey.gallerya3.model.dto.User;

public final class QueryUtils {
    private static final String GET = "GET";

    public static List<User> getUsersList(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
            Log.e("MyTAG", url.toString());
        } catch (IOException e) {

        }
        List<User> userList = extractUsersListFromJson(jsonResponse);
        return userList;
    }

    private static List<User> extractUsersListFromJson(String requestJSON) {
        if (TextUtils.isEmpty(requestJSON)) {
            return null;
        }
        List<User> users = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(requestJSON);
            for (int i = 0; i < jsonArray.length(); i++) {
                long id;
                String name;
                JSONObject currentUser = jsonArray.optJSONObject(i);
                if (currentUser == null) {
                    continue;
                }
                id = currentUser.optLong("id");
                name = currentUser.optString("name");
                users.add(new User(id, name));
                Log.i("MyTAG", String.valueOf(id + " / " + name + "\n"));
            }

        } catch (JSONException e) {
            Log.e("MyTAG", e.toString());
        }
        return users;
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
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod(GET);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {

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

        }
        return url;
    }
}
