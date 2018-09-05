package com.wojdor.hearthstonecards.application.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

import timber.log.Timber;

public class FileStorage {

    private static final String PATH_FORMAT = "%s/%s";
    private Context context;

    public FileStorage(Context context) {
        this.context = context;
    }

    public void write(String fileName, Bitmap bitmap) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
            outputStream.close();
        } catch (Exception error) {
            Timber.e(error);
        }
    }

    public File get(String fileName) {
        String path = String.format(PATH_FORMAT, context.getFilesDir().getAbsolutePath(), fileName);
        return new File(path);
    }
}
