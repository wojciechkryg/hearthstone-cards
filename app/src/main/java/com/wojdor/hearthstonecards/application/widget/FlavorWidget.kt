package com.wojdor.hearthstonecards.application.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.extension.asHtml
import com.wojdor.hearthstonecards.application.splash.SplashActivity
import com.wojdor.hearthstonecards.data.repository.CardRepository
import com.wojdor.hearthstonecards.domain.Card
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.*

class FlavorWidget : AppWidgetProvider(), KoinComponent {

    private val repository: CardRepository by inject()

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach { updateAppWidget(context, appWidgetManager, it) }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_flavor)
        setupWidgetOnClick(context, views)
        setupWidgetLayout(appWidgetManager, appWidgetId, views)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun setupWidgetOnClick(context: Context, views: RemoteViews) {
        views.setOnClickPendingIntent(R.id.widget_flavor_container_rl, createOnClickPendingIntent(context))
    }

    private fun createOnClickPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, SplashActivity::class.java)
        return PendingIntent.getActivity(context, WIDGET_REQUEST_CODE, intent, WIDGET_DEFAULT_FLAG)
    }

    private fun setupWidgetLayout(appWidgetManager: AppWidgetManager, appWidgetId: Int, views: RemoteViews) {
        Observable.fromCallable { repository.getAllCards() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { showRandomCardFlavor(appWidgetManager, appWidgetId, views, it) }
    }

    private fun showRandomCardFlavor(appWidgetManager: AppWidgetManager, appWidgetId: Int, views: RemoteViews, cards: List<Card>) {
        val randomCard = getRandomCard(cards) ?: return
        setupCardFlavor(views, randomCard)
        setupCardName(views, randomCard)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun setupCardName(views: RemoteViews, randomCard: Card) {
        val formattedName = String.format(CARD_NAME_FORMAT, randomCard.name)
        views.setTextViewText(R.id.widget_flavor_card_tv, formattedName)
    }

    private fun setupCardFlavor(views: RemoteViews, randomCard: Card) {
        val formattedFlavor = randomCard.flavorText.asHtml()
        views.setTextViewText(R.id.widget_flavor_flavor_tv, formattedFlavor)
    }

    private fun getRandomCard(cards: List<Card>): Card? {
        if (cards.isEmpty()) {
            return null
        }
        val cardIndex = Random().nextInt(cards.size)
        return cards[cardIndex]
    }

    companion object {
        private const val CARD_NAME_FORMAT = "~ %s"
        private const val WIDGET_REQUEST_CODE = 0
        private const val WIDGET_DEFAULT_FLAG = 0
    }
}

