package com.wojdor.hearthstonecards.application.util

import java.util.*

class Language {

    val currentLanguage
        get() = Locale.getDefault().run { language + country }
}
