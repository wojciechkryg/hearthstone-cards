package com.wojdor.hearthstonecards.application.classcards

import android.support.v7.widget.RecyclerView
import android.view.View

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.util.FileStorage
import com.wojdor.hearthstonecards.domain.Card
import kotlinx.android.synthetic.main.item_card.view.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ClassCardViewHolder(private val view: View) : RecyclerView.ViewHolder(view), KoinComponent {

    private val fileStorage: FileStorage by inject()

    fun bind(card: Card, onCardClick: (Card) -> Unit) {
        with(view) {
            loadCardImage(card)
            itemCardCardIv.contentDescription = card.name
            setOnClickListener { _ -> onCardClick(card) }
        }
    }

    private fun View.loadCardImage(card: Card) {
        Glide.with(context)
                .load(fileStorage.get(card.cardId))
                .apply(RequestOptions()
                        .placeholder(R.drawable.ic_card)
                        .error(R.drawable.ic_card)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(itemCardCardIv)
    }
}
