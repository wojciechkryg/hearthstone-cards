package com.wojdor.hearthstonecards.app.view

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference

class SummaryValueListPreference : ListPreference {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun getSummary(): CharSequence = entry
}
