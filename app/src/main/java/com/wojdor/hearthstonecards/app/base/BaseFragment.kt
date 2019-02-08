package com.wojdor.hearthstonecards.app.base

import androidx.fragment.app.Fragment

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    protected abstract val viewModel: T
}
