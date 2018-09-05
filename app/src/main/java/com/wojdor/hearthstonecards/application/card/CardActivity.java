package com.wojdor.hearthstonecards.application.card;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wojdor.hearthstonecards.R;
import com.wojdor.hearthstonecards.application.base.BaseActivity;
import com.wojdor.hearthstonecards.application.util.Copy;
import com.wojdor.hearthstonecards.application.util.HtmlParser;
import com.wojdor.hearthstonecards.domain.Card;

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
    @BindView(R.id.cardCardEffectLabel)
    TextView cardCardEffectLabel;
    @BindView(R.id.cardCardEffect)
    TextView cardCardEffect;
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
        HtmlParser htmlParser = new HtmlParser();
        cardCardName.setText(card.getName());
        cardCardEffect.setText(htmlParser.asHtml(card.getText()));
        cardCardSet.setText(card.getSet());
        cardCardRarity.setText(card.getRarity());
        cardCardClass.setText(card.getClassName());
        cardCardFlavor.setText(htmlParser.asHtml(card.getFlavorText()));
        cardCardArtist.setText(card.getArtist());
    }

    private void initCopy(Card card) {
        Copy copy = new Copy();
        String name = getString(R.string.card_name);
        String effect = getString(R.string.card_effect);
        String set = getString(R.string.card_set);
        String rarity = getString(R.string.card_rarity);
        String className = getString(R.string.card_class);
        String flavor = getString(R.string.card_flavor);
        String artist = getString(R.string.card_artist);
        copy.applyOnLongClick(cardCardNameLabel, name, card.getName());
        copy.applyOnLongClick(cardCardName, name, card.getName());
        copy.applyOnLongClick(cardCardEffectLabel, effect, card.getText());
        copy.applyOnLongClick(cardCardEffect, effect, card.getText());
        copy.applyOnLongClick(cardCardSetLabel, set, card.getSet());
        copy.applyOnLongClick(cardCardSet, set, card.getSet());
        copy.applyOnLongClick(cardCardRarityLabel, rarity, card.getRarity());
        copy.applyOnLongClick(cardCardRarity, rarity, card.getRarity());
        copy.applyOnLongClick(cardCardClassLabel, className, card.getClassName());
        copy.applyOnLongClick(cardCardClass, className, card.getClassName());
        copy.applyOnLongClick(cardCardFlavorLabel, flavor, card.getFlavorText());
        copy.applyOnLongClick(cardCardFlavor, flavor, card.getFlavorText());
        copy.applyOnLongClick(cardCardArtistLabel, artist, card.getArtist());
        copy.applyOnLongClick(cardCardArtist, artist, card.getArtist());
    }

    private void loadCardImage(File file) {
        Glide.with(this)
                .load(file)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(cardCardIv);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
