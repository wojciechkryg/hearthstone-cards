package com.wojdor.hearthstonecards.application.update

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.support.v4.os.ResultReceiver
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.util.CardImageDownloader
import com.wojdor.hearthstonecards.data.database.CardDatabase
import com.wojdor.hearthstonecards.data.repository.CardRepository
import com.wojdor.hearthstonecards.data.service.CardService
import com.wojdor.hearthstonecards.data.service.mapper.CardMapper
import com.wojdor.hearthstonecards.data.service.mapper.ZipResultMapper
import com.wojdor.hearthstonecards.data.service.model.CardModel
import com.wojdor.hearthstonecards.data.session.UserSession
import com.wojdor.hearthstonecards.domain.VersionInfo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UpdateIntentService : IntentService(UpdateIntentService::class.java.simpleName) {
    //TODO: It should be LifecycleService or delete that service

    private val repository by lazy {
        CardRepository(CardService.getInstance(), CardDatabase.getInstance(this).cardDao(),
                UserSession.getInstance(this)
        )
    }
    
    private val cardImageDownloader: CardImageDownloader = CardImageDownloader(this)

    override fun onHandleIntent(intent: Intent?) {
        val versionInfo = intent?.extras?.getParcelable<VersionInfo>(VERSION_INFO_EXTRA) ?: return
        val updateResultReceiver = intent.extras?.getParcelable<ResultReceiver>(UPDATE_RESULT_RECEIVER_EXTRA)
                ?: return
        val locale = userSession.locale ?: getString(R.string.settings_preferences_default_locale)
        val apiCalls = mutableListOf<Single<List<CardModel>>>()
        versionInfo.classNames.forEach { apiCalls.add(downloadDataForClass(it, locale)) }
        makeAllApiCalls(apiCalls, versionInfo, updateResultReceiver)
    }

    private fun makeAllApiCalls(apiCalls: List<Single<List<CardModel>>>, versionInfo: VersionInfo, updateResultReceiver: ResultReceiver) {
        Single.zip<List<CardModel>, List<CardModel>>(apiCalls) { ZipResultMapper.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ onSuccess(it, versionInfo, updateResultReceiver) },
                        { onError(updateResultReceiver) })
    }

    private fun downloadDataForClass(className: String, locale: String): Single<List<CardModel>> {
        return cardApi.getCollectibleCardsForClass(className, locale)
                .onErrorReturnItem(emptyList())
    }

    @SuppressLint("RestrictedApi")
    private fun onSuccess(cardModels: List<CardModel>, versionInfo: VersionInfo, updateResultReceiver: ResultReceiver) {
        val locale = userSession.locale ?: getString(R.string.settings_preferences_default_locale)
        cardModels.forEach { cardImageDownloader.getImage(it.cardId, locale) }
        val cards = CardMapper.map(cardModels)
        cardDao.insertCards(cards)
        userSession.versionInfo = versionInfo
        updateResultReceiver.send(UpdateResultReceiver.RESULT_SUCCESS, null)
    }

    @SuppressLint("RestrictedApi")
    private fun onError(updateResultReceiver: ResultReceiver?) {
        updateResultReceiver?.send(UpdateResultReceiver.RESULT_ERROR, null)
    }

    companion object {
        private const val VERSION_INFO_EXTRA = "VERSION_INFO_EXTRA"
        private const val UPDATE_RESULT_RECEIVER_EXTRA = "UPDATE_RESULT_RECEIVER_EXTRA"

        fun update(context: Context, versionInfo: VersionInfo?, updateResultReceiver: UpdateResultReceiver) {
            val intent = Intent(context, UpdateIntentService::class.java)
            intent.putExtra(VERSION_INFO_EXTRA, versionInfo)
            intent.putExtra(UPDATE_RESULT_RECEIVER_EXTRA, updateResultReceiver)
            context.startService(intent)
        }
    }
}
