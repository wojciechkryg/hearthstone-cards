package com.wojdor.hearthstonecards.app.view

import android.content.Context
import android.support.v7.preference.ListPreference
import android.util.AttributeSet

class SummaryValueListPreference : ListPreference {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun getSummary(): CharSequence = entry
}
