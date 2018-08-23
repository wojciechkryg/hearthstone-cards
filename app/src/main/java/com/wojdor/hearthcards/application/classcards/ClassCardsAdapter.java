package com.wojdor.hearthcards.application.classcards;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.domain.Card;

import java.util.ArrayList;
import java.util.List;

public class ClassCardsAdapter extends RecyclerView.Adapter<ClassCardViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private List<Card> classCards = new ArrayList<>();

    ClassCardsAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setItems(List<Card> cards) {
        classCards.clear();
        classCards.addAll(cards);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ClassCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassCardViewHolder holder, int position) {
        Card card = classCards.get(position);
        holder.bind(card, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return classCards.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Card card);
    }
}
