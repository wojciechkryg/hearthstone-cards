package com.wojdor.hearthstonecards.app.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (it: T?) -> Unit) {
    this.observe(lifecycleOwner, Observer {
        observer(it)
    })
}

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, observer: (it: T) -> Unit) {
    this.observe(lifecycleOwner, Observer {
        it?.let(observer)
    })
}
