package com.wojdor.hearthstonecards.app.util

import android.content.Context
import android.graphics.Bitmap
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

class FileStorage(private val context: Context) {

    fun write(fileName: String, bitmap: Bitmap) {
        val outputStream: FileOutputStream
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY, outputStream)
            outputStream.close()
        } catch (error: Exception) {
            Timber.e(error)
        }
    }

    fun get(fileName: String): File {
        val path = String.format(PATH_FORMAT, context.filesDir.absolutePath, fileName)
        return File(path)
    }

    companion object {
        private const val QUALITY = 50
        private const val PATH_FORMAT = "%s/%s"
    }
}
