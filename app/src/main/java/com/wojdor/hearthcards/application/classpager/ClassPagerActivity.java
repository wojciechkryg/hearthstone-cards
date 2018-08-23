package com.wojdor.hearthcards.application.classpager;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassPagerActivity extends BaseActivity {

    @BindView(R.id.classPagerClassViewPager)
    ViewPager classViewPager;
    @BindView(R.id.classPagerClassTabLayout)
    TabLayout classTabLayout;
    @BindView(R.id.classPagerAdView)
    AdView adView;

    private ClassPagerViewModel viewModel;
    private ClassPagerAdapter classPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_pager);
        ButterKnife.bind(this);
        initComponents();
        viewModel = ViewModelProviders.of(this).get(ClassPagerViewModel.class);
        viewModel.getClassesWhichHaveCards().observe(this, classNames ->
                classPagerAdapter.setItems(classNames));
    }

    private void initComponents() {
        classPagerAdapter = new ClassPagerAdapter(getSupportFragmentManager());
        classViewPager.setAdapter(classPagerAdapter);
        classTabLayout.setupWithViewPager(classViewPager);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
