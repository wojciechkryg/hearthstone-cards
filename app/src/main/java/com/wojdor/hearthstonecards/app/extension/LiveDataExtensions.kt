package com.wojdor.hearthstonecards.app.extension

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

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
