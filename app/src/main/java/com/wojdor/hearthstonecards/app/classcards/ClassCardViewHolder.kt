package com.wojdor.hearthstonecards.app.classcards

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.wojdor.hearthstonecards.app.util.ImageLoader
import com.wojdor.hearthstonecards.data.repository.CardRepository
import com.wojdor.hearthstonecards.domain.Card
import kotlinx.android.synthetic.main.item_card.view.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ClassCardViewHolder(view: View) : RecyclerView.ViewHolder(view), KoinComponent {

    private val repository: CardRepository by inject()

    fun bind(card: Card, onCardClick: (View, Card) -> Unit) {
        with(itemView) {
            loadCardImage(itemCardCardIv, card)
            itemCardCardIv.contentDescription = card.name
            itemCardCardIv.transitionName = card.cardId
            setOnClickListener { onCardClick(itemView, card) }
        }
    }

    private fun loadCardImage(itemCardCardIv: ImageView, card: Card) {
        val imageFile = repository.getCardImageFromStorage(card.cardId)
        ImageLoader.load(itemCardCardIv, imageFile)
    }
}
