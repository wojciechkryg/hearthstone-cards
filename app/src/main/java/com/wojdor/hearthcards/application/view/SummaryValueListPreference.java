package com.wojdor.hearthcards.application.view;

import android.content.Context;
import android.support.v7.preference.ListPreference;
import android.util.AttributeSet;

public class SummaryValueListPreference extends ListPreference {

    public SummaryValueListPreference(Context context) {
        super(context);
    }

    public SummaryValueListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public CharSequence getSummary() {
        return getEntry();
    }
}
