package com.wojdor.hearthstonecards.application.classcards;

import android.app.ActivityOptions;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wojdor.hearthstonecards.R;
import com.wojdor.hearthstonecards.application.base.BaseFragment;
import com.wojdor.hearthstonecards.application.card.CardActivity;
import com.wojdor.hearthstonecards.domain.Card;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassCardsFragment extends BaseFragment {

    private static final String CLASS_NAME_EXTRA = "CLASS_NAME_EXTRA";
    private static final int MIN_NUMBER_OF_COLUMNS = 2;
    private static final int COLUMN_WIDTH_DIVIDER = 300;

    @BindView(R.id.classCardsCardsRv)
    RecyclerView cardsRv;

    private ClassCardsViewModel viewModel;
    private ClassCardsAdapter classCardsAdapter;

    public static ClassCardsFragment newInstance(String className) {
        ClassCardsFragment fragment = new ClassCardsFragment();
        Bundle arguments = new Bundle();
        arguments.putString(CLASS_NAME_EXTRA, className);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_cards, container, false);
        ButterKnife.bind(this, view);
        initComponents();
        return view;
    }

    private void initComponents() {
        classCardsAdapter = new ClassCardsAdapter(this::openCardActivity);
        cardsRv.setLayoutManager(new GridLayoutManager(getContext(), calculateNumberOfColumns()));
        cardsRv.setAdapter(classCardsAdapter);
    }

    private void openCardActivity(View view, Card card) {
        ImageView imageView = view.findViewById(R.id.itemCardCardIv);
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                imageView, imageView.getTransitionName()).toBundle();
        Intent intent = new Intent(getContext(), CardActivity.class);
        intent.putExtra(CardActivity.Companion.getCARD_ID_EXTRA(), card.getCardId());
        startActivity(intent, bundle);
    }

    private int calculateNumberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int numberOfColumns = displayMetrics.widthPixels / COLUMN_WIDTH_DIVIDER;
        if (numberOfColumns < MIN_NUMBER_OF_COLUMNS) return MIN_NUMBER_OF_COLUMNS;
        return numberOfColumns;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String className = getArguments().getString(CLASS_NAME_EXTRA);
        viewModel = ViewModelProviders.of(this).get(ClassCardsViewModel.class);
        viewModel.getCardsFromClass(className).observe(this, cards -> classCardsAdapter.setItems(cards));
    }
}
