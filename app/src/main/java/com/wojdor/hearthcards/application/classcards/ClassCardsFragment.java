package com.wojdor.hearthcards.application.classcards;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseFragment;

public class ClassCardsFragment extends BaseFragment {

    private static final String CLASS_NAME_EXTRA = "CLASS_NAME_EXTRA";

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
        String className = getArguments().getString(CLASS_NAME_EXTRA);
        return inflater.inflate(R.layout.fragment_class_cards, container, false);
    }
}
