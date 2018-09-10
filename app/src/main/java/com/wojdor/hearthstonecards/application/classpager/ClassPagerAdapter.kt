package com.wojdor.hearthstonecards.application.classpager

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.wojdor.hearthstonecards.application.classcards.ClassCardsFragment
import java.util.*

class ClassPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val classNames = ArrayList<String>()

    fun setItems(items: List<String>) {
        with(classNames) {
            classNames.clear()
            classNames.addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = ClassCardsFragment.newInstance(classNames[position])

    override fun getCount() = classNames.size

    override fun getPageTitle(position: Int) = classNames[position]
}
