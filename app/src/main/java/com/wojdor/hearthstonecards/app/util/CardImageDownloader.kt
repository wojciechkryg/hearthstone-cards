package com.wojdor.hearthstonecards.app.util

import timber.log.Timber
import java.util.concurrent.ExecutionException

class CardImageDownloader(private val fileStorage: FileStorage) {

    fun downloadToStorage(fileName: String, locale: String) {
        try {
            val url = String.format(CARD_IMAGE_API, locale, fileName)
            val bitmap = ImageLoader.getBitmapFromUrl(url)
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
