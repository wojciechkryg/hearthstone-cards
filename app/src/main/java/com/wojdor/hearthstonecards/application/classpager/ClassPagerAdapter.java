package com.wojdor.hearthstonecards.application.classpager;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wojdor.hearthstonecards.application.classcards.ClassCardsFragment;

import java.util.ArrayList;
import java.util.List;

public class ClassPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> classNames = new ArrayList<>();

    public ClassPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setItems(List<String> items) {
        classNames.clear();
        classNames.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (position < classNames.size()) {
            return ClassCardsFragment.newInstance(classNames.get(position));
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return classNames.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return classNames.get(position);
    }
}
