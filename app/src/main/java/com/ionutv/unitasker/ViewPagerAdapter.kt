package com.ionutv.unitasker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(supportFragment: FragmentActivity): FragmentStateAdapter(supportFragment) {

    private val pageTitle = arrayListOf("ODD","EVEN")
    private val mFragmentTitleList = ArrayList<String>()

    override fun createFragment(position: Int): Fragment =
        OddWeekFragment.newInstance(position)

    override fun getItemCount(): Int {
        return pageTitle.size
    }

    fun getPageTitle(position:Int): String =
        pageTitle[position]
}