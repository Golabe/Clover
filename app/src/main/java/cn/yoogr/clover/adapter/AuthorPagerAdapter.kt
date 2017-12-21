package cn.yoogr.clover.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by yuequan on 2017/11/25.
 */
class AuthorPagerAdapter(fm: FragmentManager?, private val fragments:List<Fragment>) : FragmentPagerAdapter(fm) {
    companion object {
        private val titles= arrayListOf("VIDEO","ARTICLE","PHOTO")
    }
    override fun getItem(position: Int): Fragment =fragments[position]
    override fun getCount(): Int =fragments.size
    override fun getPageTitle(position: Int): CharSequence =titles[position]
}