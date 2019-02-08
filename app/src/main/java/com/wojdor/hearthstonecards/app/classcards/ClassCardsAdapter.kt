package com.wojdor.hearthstonecards.app.classcards

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.app.extension.inflate
import com.wojdor.hearthstonecards.domain.Card

class ClassCardsAdapter(private val onCardClick: (View, Card) -> Unit) : RecyclerView.Adapter<ClassCardViewHolder>() {

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
        holder.bind(classCards[position], onCardClick)
    }

    override fun getItemCount() = classCards.size
}
