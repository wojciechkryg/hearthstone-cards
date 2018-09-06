package com.wojdor.hearthstonecards.application.classcards

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.util.FileStorage
import com.wojdor.hearthstonecards.domain.Card

class ClassCardViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val itemCardCardIv: ImageView = view.findViewById(R.id.itemCardCardIv)
    private val fileStorage: FileStorage = FileStorage(view.context)

    fun bind(card: Card, onCardClick: (Card) -> Unit) {
        Glide.with(itemCardCardIv.context)
                .load(fileStorage.get(card.cardId))
                .apply(RequestOptions()
                        .placeholder(R.drawable.ic_card)
                        .error(R.drawable.ic_card)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(itemCardCardIv)
        itemCardCardIv.contentDescription = card.name
        view.setOnClickListener { _ -> onCardClick(card) }
    }
}
