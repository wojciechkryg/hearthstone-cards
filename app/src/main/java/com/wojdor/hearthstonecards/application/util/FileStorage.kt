package com.wojdor.hearthstonecards.application.util

import android.content.Context
import android.graphics.Bitmap

import java.io.File
import java.io.FileOutputStream

import timber.log.Timber

class FileStorage(private val context: Context) {

    fun write(fileName: String, bitmap: Bitmap) {
        val outputStream: FileOutputStream
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            outputStream.close()
        } catch (error: Exception) {
            Timber.e(error)
        }
    }

    operator fun get(fileName: String): File {
        val path = String.format(PATH_FORMAT, context.filesDir.absolutePath, fileName)
        return File(path)
    }

    companion object {
        private const val PATH_FORMAT = "%s/%s"
    }
}
