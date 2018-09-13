package com.wojdor.hearthstonecards.application.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import com.wojdor.hearthstonecards.R
import org.jetbrains.anko.toast

fun View.copyOnLongClick(labelResId: Int, text: String) {
    setOnLongClickListener { _ ->
        val label = context.getString(labelResId)
        val formattedText = text.fromHtml()
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.primaryClip = ClipData.newPlainText(label, formattedText)
        context.toast(R.string.text_copied)
        true
    }
}
