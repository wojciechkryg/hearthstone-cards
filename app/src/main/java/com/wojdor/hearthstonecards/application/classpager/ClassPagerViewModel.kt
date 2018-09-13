package com.wojdor.hearthstonecards.application.classpager

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel
import com.wojdor.hearthstonecards.application.extension.isPositive
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ClassPagerViewModel(application: Application) : BaseAndroidViewModel(application) {

    val classesWhichHaveCards: LiveData<List<String>>
        get() {
            val data = MutableLiveData<List<String>>()
            Observable.fromCallable {
                val classNames = userSession.versionInfo?.classNames ?: emptyList()
                classNames.filter { cardDao.getAmountOfCardsFromClass(it).isPositive }
            }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { data.setValue(it) }
            return data
        }
}
