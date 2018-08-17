package com.wojdor.hearthcards.application.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class ImageDownloader {

    private Context context;
    private FileWriter fileWriter;

    public ImageDownloader(Context context) {
        this.context = context;
        fileWriter = new FileWriter(context);
    }

    public void getImage(String fileName, String url) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        fileWriter.write(fileName, resource);
                    }
                });
    }
}
