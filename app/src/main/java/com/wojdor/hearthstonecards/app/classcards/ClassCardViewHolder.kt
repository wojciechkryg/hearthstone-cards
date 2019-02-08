package com.wojdor.hearthstonecards.app.classcards

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.app.util.FileStorage
import com.wojdor.hearthstonecards.domain.Card
import kotlinx.android.synthetic.main.item_card.view.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ClassCardViewHolder(view: View) : RecyclerView.ViewHolder(view), KoinComponent {

    private val fileStorage: FileStorage by inject()

    fun bind(card: Card, onCardClick: (View, Card) -> Unit) {
        with(itemView) {
            loadCardImage(itemCardCardIv, card)
            itemCardCardIv.contentDescription = card.name
            itemCardCardIv.transitionName = card.cardId
            setOnClickListener { onCardClick(itemView, card) }
        }
    }

    private fun loadCardImage(itemCardCardIv: ImageView, card: Card) {
        Picasso.get()
                .load(fileStorage.get(card.cardId))
                .placeholder(R.drawable.ic_card)
                .error(R.drawable.ic_card)
                .fit()
                .into(itemCardCardIv)
    }
}
