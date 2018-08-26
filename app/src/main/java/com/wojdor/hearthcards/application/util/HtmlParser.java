package com.wojdor.hearthcards.application.util;

import android.text.Html;
import android.text.Spanned;

public class HtmlParser {

    private static final String EMPTY = "";
    private static final String ENTER = "\n";
    private static final String ENTER_SIGN = "\\n";
    private static final String ENTER_HTML = "<br>";
    private static final String DOLLAR_SIGN = "$";

    public Spanned asHtml(String text) {
        if (text == null) return Html.fromHtml(EMPTY);
        String editedText = text.replace(ENTER_SIGN, ENTER_HTML).replace(DOLLAR_SIGN, EMPTY);
        return Html.fromHtml(editedText);
    }

    public String asString(String text) {
        if (text == null) return EMPTY;
        return Html.fromHtml(text).toString().replace(ENTER_SIGN, ENTER).replace(DOLLAR_SIGN, EMPTY);
    }
}
