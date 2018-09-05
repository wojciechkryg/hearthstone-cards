package com.wojdor.hearthstonecards.application.classpager;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClassPagerViewModel extends BaseAndroidViewModel {

    public ClassPagerViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<String>> getClassesWhichHaveCards() {
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        Single.fromCallable(() -> {
            List<String> classNames = getUserSession().getVersionInfo().getClassNames();
            return filterClassesWhichHaveCards(classNames);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data::setValue);
        return data;
    }

    @NonNull
    private List<String> filterClassesWhichHaveCards(List<String> classNames) {
        List<String> classNamesWithCards = new ArrayList<>();
        for (String className : classNames) {
            int amountOfCards = getCardDao().getAmountOfCardsFromClass(className);
            if (amountOfCards == 0) continue;
            classNamesWithCards.add(className);
        }
        return classNamesWithCards;
    }
}
