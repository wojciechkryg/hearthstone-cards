package com.wojdor.hearthstonecards.application.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import timber.log.Timber;

public class CardImageDownloader {

    private static final String CARD_IMAGE_API = "https://art.hearthstonejson.com/v1/render/latest/%s/256x/%s.png";

    private Context context;
    private FileStorage fileStorage;

    public CardImageDownloader(Context context) {
        this.context = context;
        fileStorage = new FileStorage(context);
    }

    public void getImage(String fileName, String locale) {
        try {
            String url = String.format(CARD_IMAGE_API, locale, fileName);
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get();
            bitmap.setHasAlpha(true);
            fileStorage.write(fileName, bitmap);
        } catch (InterruptedException | ExecutionException error) {
            Timber.e(error);
        }
    }
}
