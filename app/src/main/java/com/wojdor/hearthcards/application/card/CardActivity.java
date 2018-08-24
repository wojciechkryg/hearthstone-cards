package com.wojdor.hearthcards.application.card;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardActivity extends BaseActivity {

    public static final String CARD_ID_EXTRA = "CARD_ID_EXTRA";

    @BindView(R.id.cardToolbar)
    Toolbar cardToolbar;
    @BindView(R.id.cardCardIv)
    ImageView cardCardIv;
    @BindView(R.id.cardCardName)
    TextView cardCardName;
    @BindView(R.id.cardCardSet)
    TextView cardCardSet;
    @BindView(R.id.cardCardRarity)
    TextView cardCardRarity;
    @BindView(R.id.cardCardClass)
    TextView cardCardClass;
    @BindView(R.id.cardCardFlavor)
    TextView cardCardFlavor;
    @BindView(R.id.cardCardArtist)
    TextView cardCardArtist;

    private CardViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        initComponents();
    }

    private void initComponents() {
        initToolbar();
        initCardDetails();
    }

    private void initToolbar() {
        setSupportActionBar(cardToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initCardDetails() {
        String cardId = getIntent().getStringExtra(CARD_ID_EXTRA);
        viewModel.getCardImage(cardId).observe(this, this::loadCardImage);
        viewModel.getCardByCardId(cardId).observe(this, card -> {
            cardCardName.setText(card.getName());
            cardCardSet.setText(card.getSet());
            cardCardRarity.setText(card.getRarity());
            cardCardClass.setText(card.getClassName());
            cardCardFlavor.setText(Html.fromHtml(card.getFlavorText()));
            cardCardArtist.setText(card.getArtist());
        });
    }

    private void loadCardImage(File file) {
        Glide.with(this)
                .load(file)
                .apply(new RequestOptions().placeholder(R.drawable.ic_card).error(R.drawable.ic_card))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(cardCardIv);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
