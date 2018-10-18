package ru.pyrovsergey.gallerya3;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.pyrovsergey.gallerya3.model.pojo.Photo;
import ru.pyrovsergey.gallerya3.model.network.ImageLoader;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> photos;
    private List<AsyncTask<String, Integer, Bitmap>> loaders;

    PhotoAdapter() {
        photos = new ArrayList<>();
        loaders = new ArrayList<>();
    }

    void refreshAdapterPhotoList(List<Photo> list) {
        if (list != null) {
            int positionStart = photos.size();
            int itemCount = list.size();
            photos.addAll(list);
            notifyItemRangeChanged(positionStart, itemCount);
            Log.i("MyTAG", "Adapter size = " + photos.size());
        }
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, int position) {
        Photo currentPhoto = photos.get(position);
        holder.textViewTitle.setText(currentPhoto.getTitle());
        holder.imageViewPhoto.setImageDrawable(null);
        loaders.add(new ImageLoader(holder.imageViewPhoto).execute(currentPhoto.getUrl()));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    void stopAllDownloads() {
        if (!loaders.isEmpty()) {
            for (int i = 0; i < loaders.size(); i++) {
                loaders.get(i).cancel(true);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        ImageView imageViewPhoto;
        TextView textViewTitle;

        ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            imageViewPhoto = itemView.findViewById(R.id.image_view_photo);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
        }
    }
}
