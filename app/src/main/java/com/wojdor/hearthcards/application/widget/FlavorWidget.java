package com.wojdor.hearthcards.application.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.Spanned;
import android.widget.RemoteViews;

import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.splash.SplashActivity;
import com.wojdor.hearthcards.application.util.HtmlParser;
import com.wojdor.hearthcards.data.database.CardDatabase;
import com.wojdor.hearthcards.data.database.dao.CardDao;
import com.wojdor.hearthcards.domain.Card;

import java.util.List;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FlavorWidget extends AppWidgetProvider {

    private static final int WIDGET_REQUEST_CODE = 0;
    private static final int WIDGET_DEFAULT_FLAG = 0;

    private CardDao cardDao;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        cardDao = CardDatabase.getInstance(context).cardDao();
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_flavor);
        setupWidgetOnClick(context, views);
        setupWidgetFlavor(appWidgetManager, appWidgetId, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void setupWidgetOnClick(Context context, RemoteViews views) {
        views.setOnClickPendingIntent(R.id.widget_flavor_container_rl, createOnClickPendingIntent(context));
    }

    private PendingIntent createOnClickPendingIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return PendingIntent.getActivity(context, WIDGET_REQUEST_CODE, intent, WIDGET_DEFAULT_FLAG);
    }

    private void setupWidgetFlavor(AppWidgetManager appWidgetManager, int appWidgetId, RemoteViews views) {
        Single.fromCallable(() -> cardDao.getAllCards())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cards -> {
                    Spanned formattedFlavor = new HtmlParser().asHtml(getRandomFlavor(cards));
                    views.setTextViewText(R.id.widget_flavor_flavor_tv, formattedFlavor);
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                });
    }

    private String getRandomFlavor(List<Card> cards) {
        Random random = new Random();
        int cardIndex = random.nextInt(cards.size());
        return cards.get(cardIndex).getFlavorText();
    }
}

