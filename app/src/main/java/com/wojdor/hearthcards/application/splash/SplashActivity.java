package com.wojdor.hearthcards.application.splash;

import android.content.Intent;
import android.os.Bundle;

import com.wojdor.hearthcards.application.base.BaseActivity;
import com.wojdor.hearthcards.application.classpager.ClassPagerActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchClassPagerActivity();
    }

    private void launchClassPagerActivity() {
        Intent intent = new Intent(this, ClassPagerActivity.class);
        startActivity(intent);
        finish();
    }
}
