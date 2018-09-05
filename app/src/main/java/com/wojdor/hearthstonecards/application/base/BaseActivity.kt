package com.wojdor.hearthstonecards.application.base

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<T : BaseAndroidViewModel> : AppCompatActivity() {

    protected abstract val viewModelClass: Class<T>

    protected val viewModel: T by lazy {
        ViewModelProviders.of(this).get(viewModelClass)
    }
}
