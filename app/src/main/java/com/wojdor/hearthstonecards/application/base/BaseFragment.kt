package com.wojdor.hearthstonecards.application.base

import android.support.v4.app.Fragment

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    protected abstract val viewModel: T
}
