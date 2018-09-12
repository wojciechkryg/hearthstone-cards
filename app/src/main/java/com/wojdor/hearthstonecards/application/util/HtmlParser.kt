package com.wojdor.hearthstonecards.application.util

import android.text.Html
import android.text.Spanned

class HtmlParser {

    fun asHtml(text: String?): Spanned {
        if (text == null) return Html.fromHtml(EMPTY)
        val editedText = text
                .replace(ENTER_SIGN, ENTER_HTML)
                .replace(DOLLAR_SIGN, EMPTY)
                .replace(UNDERLINE, SPACE)
                .replace(WEIRD_X_SYMBOL, EMPTY)
        return Html.fromHtml(editedText)
    }

    fun asString(text: String?): String {
        return if (text == null) EMPTY else Html.fromHtml(text).toString()
                .replace(ENTER_SIGN, ENTER)
                .replace(DOLLAR_SIGN, EMPTY)
                .replace(UNDERLINE, SPACE)
                .replace(WEIRD_X_SYMBOL, EMPTY)
    }

    companion object {
        private const val EMPTY = ""
        private const val SPACE = " "
        private const val ENTER = "\n"
        private const val ENTER_SIGN = "\\n"
        private const val ENTER_HTML = "<br>"
        private const val DOLLAR_SIGN = "$"
        private const val UNDERLINE = "_"
        private const val WEIRD_X_SYMBOL = "[x]"
    }
}
