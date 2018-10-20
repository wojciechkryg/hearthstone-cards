package com.wojdor.hearthstonecards.application.base

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModel: T
}
