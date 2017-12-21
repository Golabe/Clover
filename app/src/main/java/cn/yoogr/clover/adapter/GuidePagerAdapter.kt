package cn.yoogr.clover.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by yuequan on 2017/11/24.
 */
class GuidePagerAdapter(fm: FragmentManager?, private val fragments:List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment =fragments[position]
    override fun getCount(): Int =fragments.size
}