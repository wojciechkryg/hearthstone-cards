package com.wojdor.hearthcards.application.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import timber.log.Timber;

public class ImageDownloader {

    private Context context;
    private FileStorage fileStorage;

    public ImageDownloader(Context context) {
        this.context = context;
        fileStorage = new FileStorage(context);
    }

    public void getImage(String fileName, String url) {
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get();
            fileStorage.write(fileName, bitmap);
        } catch (InterruptedException | ExecutionException error) {
            Timber.e(error);
        }
    }
}
