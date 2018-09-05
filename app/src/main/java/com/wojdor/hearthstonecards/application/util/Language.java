package com.wojdor.hearthstonecards.application.util;

import java.util.Locale;

public class Language {

    public String getCurrentLanguage() {
        Locale defaultLocale = Locale.getDefault();
        return defaultLocale.getLanguage() + defaultLocale.getCountry();
    }
}
