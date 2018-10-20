package com.wojdor.hearthstonecards.application.classpager

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.base.BaseActivity
import com.wojdor.hearthstonecards.application.extension.observeNonNull
import com.wojdor.hearthstonecards.application.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_class_pager.*
import org.koin.android.viewmodel.ext.android.viewModel

class ClassPagerActivity : BaseActivity<ClassPagerViewModel>() {

    override val viewModel: ClassPagerViewModel by viewModel()

    private lateinit var classPagerAdapter: ClassPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_pager)
        initComponents()
        viewModel.classesWhichHaveCards.observeNonNull(this) { classPagerAdapter.setItems(it) }
    }

    private fun initComponents() {
        setSupportActionBar(classPagerToolbar)
        initClassViewPager()
    }

    private fun initClassViewPager() {
        classPagerAdapter = ClassPagerAdapter(supportFragmentManager)
        classPagerClassViewPager.adapter = classPagerAdapter
        classPagerClassTabLayout.setupWithViewPager(classPagerClassViewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_class_pager, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.classPagerSettings -> openSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSettings() {
        val bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent, bundle)
    }
}
