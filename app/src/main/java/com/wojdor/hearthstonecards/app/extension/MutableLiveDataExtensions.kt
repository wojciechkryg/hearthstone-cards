package com.wojdor.hearthstonecards.app.extension

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.setDifferentValue(newValue: T) {
    if (value == newValue) return
    value = newValue
}
