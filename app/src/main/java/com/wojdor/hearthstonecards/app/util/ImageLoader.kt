package com.wojdor.hearthstonecards.app.util

import android.graphics.Bitmap
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.File

object ImageLoader {

    fun getBitmapFromUrl(url: String): Bitmap {
        return Picasso.get()
                .load(url)
                .get()
    }

    fun load(source: File, placeholder: Int, error: Int, into: ImageView) {
        Picasso.get()
                .load(source)
                .placeholder(placeholder)
                .error(error)
                .fit()
                .into(into)
    }
}
