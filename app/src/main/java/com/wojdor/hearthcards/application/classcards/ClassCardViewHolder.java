package com.wojdor.hearthcards.application.classcards;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.util.FileStorage;
import com.wojdor.hearthcards.domain.Card;

class ClassCardViewHolder extends RecyclerView.ViewHolder {

    private final View view;
    private final ImageView cardIv;
    private FileStorage fileStorage;

    ClassCardViewHolder(View view) {
        super(view);
        this.fileStorage = new FileStorage(view.getContext());
        this.view = view;
        cardIv = view.findViewById(R.id.itemCardCardIv);
    }

    public void bind(Card card, ClassCardsAdapter.OnItemClickListener onItemClickListener) {
        Glide.with(cardIv.getContext())
                .load(fileStorage.get(card.getCardId()))
                .apply(new RequestOptions().placeholder(R.drawable.ic_card).error(R.drawable.ic_card))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(cardIv);
        cardIv.setContentDescription(card.getName());
        view.setOnClickListener(view -> onItemClickListener.onItemClick(card));
    }
}
