package com.wojdor.hearthstonecards.application.extension

import android.text.Spanned
import com.wojdor.hearthstonecards.application.util.HtmlParser

fun String.asHtml(): Spanned = HtmlParser().asHtml(this)

fun String.fromHtml(): String = HtmlParser().asString(this)
