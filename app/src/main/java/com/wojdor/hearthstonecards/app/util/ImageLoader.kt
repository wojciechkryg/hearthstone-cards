package com.wojdor.hearthstonecards.app.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.wojdor.hearthstonecards.R
import java.io.File

object ImageLoader {

    fun getBitmapFromUrl(url: String): Bitmap {
        return Picasso.get()
                .load(url)
                .get()
    }

    fun load(imageView: ImageView, file: File, errorResId: Int = R.drawable.ic_card) {
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            imageView.setImageBitmap(bitmap)
        } else {
            imageView.setImageResource(errorResId)
        }
    }
}
