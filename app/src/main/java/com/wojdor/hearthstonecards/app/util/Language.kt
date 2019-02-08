package com.wojdor.hearthstonecards.app.util

import java.util.*

class Language {

    val currentLanguage
        get() = Locale.getDefault().run { language + country }
}
