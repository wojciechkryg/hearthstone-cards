package com.wojdor.hearthstonecards.app.classpager

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.wojdor.hearthstonecards.app.classcards.ClassCardsFragment
import java.util.*

class ClassPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val classNames = ArrayList<String>()

    fun setItems(items: List<String>) {
        with(classNames) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = ClassCardsFragment.newInstance(classNames[position])

    override fun getCount() = classNames.size

    override fun getPageTitle(position: Int) = classNames[position]
}
