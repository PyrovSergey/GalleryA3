package ru.pyrovsergey.gallerya3.model.network;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.utils.ConnectionUtils;

public class ImageLoader extends AsyncTask<String, Integer, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    private ImageView image;

    public ImageLoader(ImageView image) {
        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap bmp = loadBitmap(urls[0]);
        if (bmp == null) {
            bmp = downloadBitmapInNetwork(urls[0]);
        }
        return bmp;
    }

    @NonNull
    private Bitmap downloadBitmapInNetwork(String url) {
        Bitmap bmp = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bmp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        assert bmp != null;
        saveBitmap(bmp, url.hashCode());
        return bmp;
    }

    private void saveBitmap(Bitmap bitmap, int hashcode) {
        String savedImagePath;
        String hashLink = String.valueOf(hashcode);
        String imageFileName = "JPEG_" + hashLink + ".jpg";
        File storageDir = App.getInstance().getCacheDir();
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            Log.i("MyTAG", savedImagePath + " path");
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("MyTAG", imageFileName + " saved to cache");
        }
    }

    private Bitmap loadBitmap(String url) {
        String hashLink = String.valueOf(url.hashCode());
        String imageFileName = "JPEG_" + hashLink + ".jpg";
        Bitmap bmp = null;
        File storageDir = App.getInstance().getCacheDir();
        if (storageDir.exists()) {
            Log.i("MyTAG", storageDir + " storage directory exists");
            Log.i("MyTAG", imageFileName + " attempt to upload file " + storageDir.getPath() + "/" + imageFileName);
            bmp = BitmapFactory.decodeFile(storageDir.getPath() + "/" + imageFileName);
            if (bmp != null) {
                Log.i("MyTAG", imageFileName + " loaded from cache");
            } else {
                Log.i("MyTAG", imageFileName + " this image is not in the cache");
            }
        } else {
            Log.i("MyTAG", imageFileName + " storage directory does not exist");
        }
        return bmp;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.i("TAG_PROGRESS", values[0].toString());
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            image.setImageBitmap(bitmap);
        } else {
            ConnectionUtils.checkInternetConnection();
        }
        image = null;
    }
}
