package com.wojdor.hearthstonecards.app.card

import android.os.Bundle
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.app.base.BaseActivity
import com.wojdor.hearthstonecards.app.extension.asHtml
import com.wojdor.hearthstonecards.app.extension.observeNonNull
import com.wojdor.hearthstonecards.app.util.ImageLoader
import com.wojdor.hearthstonecards.domain.Card
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.layout_card_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File

class CardActivity : BaseActivity<CardViewModel>() {

    override val viewModel: CardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        initComponents()
    }

    private fun initComponents() {
        val cardId = intent.getStringExtra(CARD_ID_EXTRA)
        cardCardIv.transitionName = cardId
        initToolbar()
        initDetails(cardId)
    }

    private fun initToolbar() {
        setSupportActionBar(cardToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initDetails(cardId: String) {
        viewModel.getCardImage(cardId).observeNonNull(this) { loadCardImage(it) }
        viewModel.getCardByCardId(cardId).observeNonNull(this) { initCardDetails(it) }
    }

    private fun initCardDetails(card: Card) {
        with(card) {
            cardCardIv.contentDescription = name
            cardNameCardDetail.text = name
            cardEffectCardDetail.text = text.asHtml()
            cardSetCardDetail.text = set
            cardRarityCardDetail.text = rarity
            cardClassCardDetail.text = className
            cardFlavorCardDetail.text = flavorText.asHtml()
            cardArtistCardDetail.text = artist
        }
    }

    private fun loadCardImage(file: File) {
        ImageLoader.load(cardCardIv, file)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val CARD_ID_EXTRA = "CARD_ID_EXTRA"
    }
}
