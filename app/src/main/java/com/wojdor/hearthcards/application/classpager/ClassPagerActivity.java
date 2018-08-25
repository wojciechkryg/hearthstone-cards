package com.wojdor.hearthcards.application.classpager;

import android.app.ActivityOptions;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseActivity;
import com.wojdor.hearthcards.application.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassPagerActivity extends BaseActivity {

    @BindView(R.id.classPagerToolbar)
    Toolbar classPagerToolbar;
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
        setSupportActionBar(classPagerToolbar);
        initClassViewPager();
        initAd();
    }

    private void initClassViewPager() {
        classPagerAdapter = new ClassPagerAdapter(getSupportFragmentManager());
        classViewPager.setAdapter(classPagerAdapter);
        classTabLayout.setupWithViewPager(classViewPager);
    }

    private void initAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_class_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.classPagerSettings) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent, bundle);
        }
        return super.onOptionsItemSelected(item);
    }
}
