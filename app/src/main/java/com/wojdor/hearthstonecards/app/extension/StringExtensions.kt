package com.wojdor.hearthstonecards.app.extension

import android.text.Spanned
import com.wojdor.hearthstonecards.app.util.HtmlParser

fun String.asHtml(): Spanned = HtmlParser().asHtml(this)

fun String.fromHtml(): String = HtmlParser().asString(this)

val String.Companion.empty
    get() = ""