package com.wojdor.hearthstonecards.application.card

import android.os.Bundle
import com.squareup.picasso.Picasso
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.base.BaseActivity
import com.wojdor.hearthstonecards.application.extension.asHtml
import com.wojdor.hearthstonecards.application.extension.copyOnLongClick
import com.wojdor.hearthstonecards.application.extension.observeNonNull
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
        viewModel.getCardByCardId(cardId).observeNonNull(this) {
            initCardDetails(it)
            initCopy(it)
        }
    }

    private fun initCardDetails(card: Card) {
        with(card) {
            cardCardName.text = name
            cardCardEffect.text = text.asHtml()
            cardCardSet.text = set
            cardCardRarity.text = rarity
            cardCardClass.text = className
            cardCardFlavor.text = flavorText.asHtml()
            cardCardArtist.text = artist
        }
    }

    private fun initCopy(card: Card) {
        with(card) {
            cardCardNameLabel.copyOnLongClick(R.string.card_name, name)
            cardCardName.copyOnLongClick(R.string.card_name, name)
            cardCardEffectLabel.copyOnLongClick(R.string.card_effect, text)
            cardCardEffect.copyOnLongClick(R.string.card_effect, text)
            cardCardSetLabel.copyOnLongClick(R.string.card_set, set)
            cardCardSet.copyOnLongClick(R.string.card_set, set)
            cardCardRarityLabel.copyOnLongClick(R.string.card_rarity, rarity)
            cardCardRarity.copyOnLongClick(R.string.card_rarity, rarity)
            cardCardClassLabel.copyOnLongClick(R.string.card_class, className)
            cardCardClass.copyOnLongClick(R.string.card_class, className)
            cardCardFlavorLabel.copyOnLongClick(R.string.card_flavor, flavorText)
            cardCardFlavor.copyOnLongClick(R.string.card_flavor, flavorText)
            cardCardArtistLabel.copyOnLongClick(R.string.card_artist, artist)
            cardCardArtist.copyOnLongClick(R.string.card_artist, artist)
        }
    }

    private fun loadCardImage(file: File) {
        Picasso.get()
                .load(file)
                .placeholder(R.drawable.ic_card)
                .error(R.drawable.ic_card)
                .fit()
                .into(cardCardIv)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val CARD_ID_EXTRA = "CARD_ID_EXTRA"
    }
}
