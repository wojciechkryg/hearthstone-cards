package com.wojdor.hearthstonecards.application.util

import com.squareup.picasso.Picasso
import timber.log.Timber
import java.util.concurrent.ExecutionException

class CardImageDownloader(private val fileStorage: FileStorage) {

    fun getImage(fileName: String, locale: String) {
        try {
            val url = String.format(CARD_IMAGE_API, locale, fileName)
            val bitmap = Picasso.get()
                    .load(url)
                    .get()
            fileStorage.write(fileName, bitmap)
        } catch (error: IllegalStateException) {
            Timber.e(error)
        } catch (error: ExecutionException) {
            Timber.e(error)
        }
    }

    companion object {
        private const val CARD_IMAGE_API = "https://art.hearthstonejson.com/v1/render/latest/%s/256x/%s.png"
    }
}
