package com.wojdor.hearthstonecards.application.base

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment

abstract class BaseFragment<T : BaseAndroidViewModel> : Fragment() {

    protected abstract val viewModelClass: Class<T>

    protected val viewModel: T by lazy {
        ViewModelProviders.of(this).get(viewModelClass)
    }
}
