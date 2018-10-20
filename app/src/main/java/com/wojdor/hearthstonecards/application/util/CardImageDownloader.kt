package com.wojdor.hearthstonecards.application.util

import android.content.Context
import com.bumptech.glide.Glide
import timber.log.Timber
import java.util.concurrent.ExecutionException

class CardImageDownloader(private val context: Context, private val fileStorage: FileStorage) {

    fun getImage(fileName: String, locale: String) {
        try {
            val url = String.format(CARD_IMAGE_API, locale, fileName)
            // TODO: Use Picasso instead of Glide
            val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get().apply {
                        setHasAlpha(true)
                    }
            fileStorage.write(fileName, bitmap)
        } catch (error: InterruptedException) {
            Timber.e(error)
        } catch (error: ExecutionException) {
            Timber.e(error)
        }
    }

    companion object {
        private const val CARD_IMAGE_API = "https://art.hearthstonejson.com/v1/render/latest/%s/256x/%s.png"
    }
}
