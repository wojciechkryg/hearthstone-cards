package com.wojdor.hearthcards.application.card;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseActivity;
import com.wojdor.hearthcards.application.util.Copy;
import com.wojdor.hearthcards.application.util.HtmlParser;
import com.wojdor.hearthcards.domain.Card;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardActivity extends BaseActivity {

    public static final String CARD_ID_EXTRA = "CARD_ID_EXTRA";

    @BindView(R.id.cardToolbar)
    Toolbar cardToolbar;
    @BindView(R.id.cardCardIv)
    ImageView cardCardIv;
    @BindView(R.id.cardCardNameLabel)
    TextView cardCardNameLabel;
    @BindView(R.id.cardCardName)
    TextView cardCardName;
    @BindView(R.id.cardCardSetLabel)
    TextView cardCardSetLabel;
    @BindView(R.id.cardCardSet)
    TextView cardCardSet;
    @BindView(R.id.cardCardRarityLabel)
    TextView cardCardRarityLabel;
    @BindView(R.id.cardCardRarity)
    TextView cardCardRarity;
    @BindView(R.id.cardCardClassLabel)
    TextView cardCardClassLabel;
    @BindView(R.id.cardCardClass)
    TextView cardCardClass;
    @BindView(R.id.cardCardFlavorLabel)
    TextView cardCardFlavorLabel;
    @BindView(R.id.cardCardFlavor)
    TextView cardCardFlavor;
    @BindView(R.id.cardCardArtistLabel)
    TextView cardCardArtistLabel;
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
        initDetails();
    }

    private void initToolbar() {
        setSupportActionBar(cardToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDetails() {
        String cardId = getIntent().getStringExtra(CARD_ID_EXTRA);
        viewModel.getCardImage(cardId).observe(this, this::loadCardImage);
        viewModel.getCardByCardId(cardId).observe(this, card -> {
            initCardDetails(card);
            initCopy(card);
        });
    }

    private void initCardDetails(Card card) {
        cardCardName.setText(card.getName());
        cardCardSet.setText(card.getSet());
        cardCardRarity.setText(card.getRarity());
        cardCardClass.setText(card.getClassName());
        cardCardFlavor.setText(new HtmlParser().asHtml((card.getFlavorText())));
        cardCardArtist.setText(card.getArtist());
    }

    private void initCopy(Card card) {
        Copy copy = new Copy();
        copy.applyOnLongClick(cardCardNameLabel, card.getName());
        copy.applyOnLongClick(cardCardName, card.getName());
        copy.applyOnLongClick(cardCardSetLabel, card.getSet());
        copy.applyOnLongClick(cardCardSet, card.getSet());
        copy.applyOnLongClick(cardCardRarityLabel, card.getRarity());
        copy.applyOnLongClick(cardCardRarity, card.getRarity());
        copy.applyOnLongClick(cardCardClassLabel, card.getClassName());
        copy.applyOnLongClick(cardCardClass, card.getClassName());
        copy.applyOnLongClick(cardCardFlavorLabel, card.getFlavorText());
        copy.applyOnLongClick(cardCardFlavor, card.getFlavorText());
        copy.applyOnLongClick(cardCardArtistLabel, card.getArtist());
        copy.applyOnLongClick(cardCardArtist, card.getArtist());
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
