package com.wojdor.hearthstonecards.application.classcards

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.extension.inflate
import com.wojdor.hearthstonecards.domain.Card

class ClassCardsAdapter(private val onCardClick: (Card) -> Unit) : RecyclerView.Adapter<ClassCardViewHolder>() {

    var classCards = mutableListOf<Card>()

    fun setItems(cards: List<Card>) {
        with(classCards) {
            clear()
            addAll(cards)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassCardViewHolder {
        val view = parent.inflate(R.layout.item_card)
        return ClassCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassCardViewHolder, position: Int) {
        val card = classCards[position]
        holder.bind(card, onCardClick)
    }

    override fun getItemCount() = classCards.size
}
