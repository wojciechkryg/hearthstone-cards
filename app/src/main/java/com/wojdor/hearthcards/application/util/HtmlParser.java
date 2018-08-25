package com.wojdor.hearthcards.application.util;

import android.text.Html;
import android.text.Spanned;

public class HtmlParser {

    private static final String ENTER = "\n";
    private static final String ENTER_HTML = "<br>";

    public Spanned asHtml(String text) {
        if (text == null) return Html.fromHtml("");
        String editedText = text.replace(ENTER, ENTER_HTML);
        return Html.fromHtml(editedText);
    }
}
