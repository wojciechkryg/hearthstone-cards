package com.wojdor.hearthcards.application.card;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardActivity extends BaseActivity {

    @BindView(R.id.cardCardIv)
    ImageView cardCardIv;

    private CardViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(CardViewModel.class);
    }
}
