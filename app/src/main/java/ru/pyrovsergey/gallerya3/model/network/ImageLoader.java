package ru.pyrovsergey.gallerya3.model.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import ru.pyrovsergey.gallerya3.app.App;
import ru.pyrovsergey.gallerya3.model.network.utils.ConnectionUtils;

public class ImageLoader extends AsyncTask<String, Integer, Bitmap> {

    private static final String JPEG = "JPEG_";
    private static final String JPG = ".jpg";
    private WeakReference<ImageView> image;

    public ImageLoader(ImageView image) {
        this.image = new WeakReference<>(image);
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
            e.printStackTrace();
        }
        assert bmp != null;
        saveBitmap(bmp, url.hashCode());
        return bmp;
    }

    private void saveBitmap(Bitmap bitmap, int hashcode) {
        String hashLink = String.valueOf(hashcode);
        String imageFileName = JPEG + hashLink + JPG;
        File storageDir = App.getInstance().getCacheDir();
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap loadBitmap(String url) {
        String hashLink = String.valueOf(url.hashCode());
        String imageFileName = JPEG + hashLink + JPG;
        Bitmap bmp = null;
        File storageDir = App.getInstance().getCacheDir();
        if (storageDir.exists()) {
            bmp = BitmapFactory.decodeFile(storageDir.getPath() + "/" + imageFileName);
        }
        return bmp;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null && image.get() != null) {
            image.get().setImageBitmap(bitmap);
        } else {
            ConnectionUtils.checkInternetConnection();
        }
    }
}
