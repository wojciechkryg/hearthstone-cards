package com.wojdor.hearthstonecards.app.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModel: T
}
