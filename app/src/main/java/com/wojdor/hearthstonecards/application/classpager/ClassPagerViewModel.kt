package com.wojdor.hearthstonecards.application.classpager

import android.app.Application
import android.arch.lifecycle.LiveData
import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel

class ClassPagerViewModel(application: Application) : BaseAndroidViewModel(application) {

    val classesWhichHaveCards: LiveData<List<String>>
        get() = repository.classesWhichHaveCards
}
