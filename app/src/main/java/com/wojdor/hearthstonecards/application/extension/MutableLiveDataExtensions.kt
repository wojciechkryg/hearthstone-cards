package com.wojdor.hearthstonecards.application.extension

import android.arch.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.setDifferentValue(newValue: T) {
    if (value == newValue) return
    value = newValue
}
