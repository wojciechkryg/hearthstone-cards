package com.wojdor.hearthcards.application.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.FileOutputStream;

import timber.log.Timber;

public class FileWriter {

    private Context context;

    public FileWriter(Context context) {
        this.context = context;
    }

    public void write(String fileName, byte[] content) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content);
            outputStream.close();
        } catch (Exception error) {
            Timber.e(error);
        }
    }

    public void write(String fileName, Bitmap bitmap) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (Exception error) {
            Timber.e(error);
        }
    }
}
