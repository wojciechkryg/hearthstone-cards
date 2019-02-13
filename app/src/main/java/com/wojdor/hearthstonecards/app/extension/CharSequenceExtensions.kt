package com.wojdor.hearthstonecards.app.extension

import com.wojdor.hearthstonecards.app.util.HtmlParser

fun CharSequence.fromHtml(): String = HtmlParser().asString(this.toString())
